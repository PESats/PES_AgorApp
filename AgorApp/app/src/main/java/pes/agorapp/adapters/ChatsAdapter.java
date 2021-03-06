package pes.agorapp.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pes.agorapp.JSONObjects.Chat;
import pes.agorapp.R;

/**
 * Created by Alex on 01-Nov-17.
 */

public class ChatsAdapter extends ArrayAdapter<Chat> {

    public ChatsAdapter(Context context, List<Chat> chats) {
        super(context, 0, chats);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Chat chat = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chat, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.item_chat_name);
        TextView title = (TextView) convertView.findViewById(R.id.item_chat_announcement);
        // Populate the data into the template view using the data object
        if (chat.getUseOwnerName()) {
            name.setText(chat.getOwnerName());
        }
        else {
            name.setText(chat.getUser().getName());
        }
        title.setText(chat.getBid().getAnunci().getTitle());
        // Return the completed view to render on screen
        return convertView;
    }
}
