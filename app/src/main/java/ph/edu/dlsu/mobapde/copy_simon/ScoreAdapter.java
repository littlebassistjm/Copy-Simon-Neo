package ph.edu.dlsu.mobapde.copy_simon;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abdul Bandlang on 11/13/2017.
 */

public class ScoreAdapter extends CursorRecyclerViewAdapter<ScoreAdapter.ScoreViewHolder>{
        //RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
        //ArrayList<Score> data;

        // user-defined constructor
        public ScoreAdapter (Context context, Cursor cursor /*,ArrayList<Score> data*/){
            super(context,cursor);
            //this.data = data;
        }

        @Override
        public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // DONE: duplicate  template item_teacher into the RecyclerView

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);

                return new ScoreViewHolder(v);
                }

        @Override
        public void onBindViewHolder(ScoreViewHolder holder, Cursor cursor) {
                //(ScoreViewHolder holder, int position) {
                //TODO: places data inside the tvTeacher
                // tvTeacher's text must be set to the data at this position
                //Score  currentScore = data.get(position);
            long id =cursor.getLong(cursor.getColumnIndex(Score.COLUMN_ID));
            String name=cursor.getString(cursor.getColumnIndex(Score.COLUMN_NAME));
            int points=cursor.getInt(cursor.getColumnIndex(Score.COLUMN_POINTS));
            holder.position.setText(""+(id));
            holder.name.setText(name);
            holder.points.setText(""+points);
            holder.itemView.setTag(id);
                // change bakground color
                //String[] colors = {"#FFFFFF", "#000000", "#000FF000", "#FFF00FFF"};
                //String color = colors[position%colors.length];
                //holder.tvTeacher.setBackgroundColor(Color.parseColor(color));
        }



        // "holds" the views
        // put the things that you want to see in each "item" here, like picture and name for contact
        public class ScoreViewHolder extends RecyclerView.ViewHolder{
            TextView position,name,points;
            public ScoreViewHolder(View itemView) {
                super(itemView);
                // what is itemView? itemView == inflated item_teacher

                //DONE: initialize tvTeacher
                this.position = (TextView)itemView.findViewById(R.id.hspos);
                this.name=(TextView)itemView.findViewById(R.id.hsname);
                this.points=(TextView)itemView.findViewById(R.id.hspoint);

            }
        }
}
