package pes.agorapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Bid;
import pes.agorapp.JSONObjects.Chat;
import pes.agorapp.R;
import pes.agorapp.adapters.ChatsAdapter;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.helpers.ObjectsHelper;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;


public class ChatListFragment extends Fragment {
    private List<Chat> chats = new ArrayList<>();
    private List<Bid> bids = new ArrayList<>();

    private PreferencesAgorApp prefs;
    private OnFragmentInteractionListener mListener;
    private ChatsAdapter adapter;

    public ChatListFragment() {
        // Required empty public constructor
    }


    public static ChatListFragment newInstance(String param1, String param2) {
        ChatListFragment fragment = new ChatListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prefs = new PreferencesAgorApp(getActivity());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onChatSelected(Chat chat);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestChats();
        adapter = new ChatsAdapter(getActivity(), chats);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) getView().findViewById(R.id.listChats);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chat chat = (Chat) listView.getItemAtPosition(position);
                mListener.onChatSelected(chat);
            }
        });
        listView.setAdapter(adapter);
    }

    private void requestChats() {
        chats.clear();
        AgorAppApiManager
                .getService()
                .getBidsWithFilters(Integer.valueOf(prefs.getId()), prefs.getActiveToken(), "selected")
                .enqueue(new retrofit2.Callback<ArrayList<Bid>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Bid>> call, Response<ArrayList<Bid>> response) {
                        bids = response.body();
                        for (Bid bid : bids) {
                            if (!bid.getAnunci().getStatus().equals("completed")) {
                                Chat chat = new Chat();
                                chat.setUser(bid.getUser());
                                chat.setLastMessage(ObjectsHelper.getFakeMessage());
                                chat.setLastMessageDate(ObjectsHelper.getFakeDate());
                                chat.setBid(bid);

                                chats.add(chat);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Bid>> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });

        AgorAppApiManager
                .getService()
                .getBidsWithFilters(Integer.valueOf(prefs.getId()), prefs.getActiveToken(), "accepted")
                .enqueue(new retrofit2.Callback<ArrayList<Bid>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Bid>> call, Response<ArrayList<Bid>> response) {
                        bids = response.body();
                        for (Bid bid : bids) {
                            if (!bid.getAnunci().getStatus().equals("completed")) {
                                Chat chat = new Chat();
                                chat.setUser(bid.getUser());
                                chat.setLastMessage(ObjectsHelper.getFakeMessage());
                                chat.setLastMessageDate(ObjectsHelper.getFakeDate());
                                chat.setBid(bid);
                                chat.setOwnerName(bid.getOwner_name());

                                chats.add(chat);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Bid>> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });
    }
}
