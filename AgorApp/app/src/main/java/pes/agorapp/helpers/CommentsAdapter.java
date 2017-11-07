package pes.agorapp.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.R;

/**
 * Created by Alex on 01-Nov-17.
 */

public class CommentsAdapter extends ArrayAdapter<Comment> {

    public CommentsAdapter(Context context, List<Comment> comments) {
        super(context, 0, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Comment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.comment_title);
        TextView text = (TextView) convertView.findViewById(R.id.comment_text);
        TextView date = (TextView) convertView.findViewById(R.id.comment_date);
        // Populate the data into the template view using the data object
        title.setText(comment.getAuthor());
        text.setText(comment.getContent());
        date.setText(comment.getDateString());
        // Return the completed view to render on screen
        return convertView;
    }
}
