package com.example.sports;


import java.util.ArrayList;










import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
	 
	static ArrayList<String[]> groups;
    static Infodb db;
    static Context ctx;
    private int lastPosition = -1;
    private View.OnLongClickListener onLongClickListener;

	
    

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<String[]> groups,Context context) {
         
         this.groups = groups;
         db=new Infodb(context);
         ctx=context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
    	System.out.println("yahaaaaa");
    	View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        // set the view's size, margins, paddings and layout parameters
    	 
        ViewHolder vh = new ViewHolder(v);
        vh.itemView.setTag(vh);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
    	System.out.println("dddddd " + i + "  " + viewHolder.idd.getText());

        String str[]=groups.get(i);
      //  System.out.println("valuuu  "+str[0]+"  "+str[1]+"  "+str[2]);
        ViewHolder.sportsName.setText(str[1]);
  //      personViewHolder.personAge.setText(str[2]);
        ViewHolder.idd.setText(str[0]);
        System.out.println("ddddddddddddd");
        setAnimation(viewHolder.container, i);
        //  personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);


       
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    
    
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
    	 return groups.size();
    }
	
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        onLongClickListener = onLongClickListener;
    }
    public View.OnLongClickListener getOnItemClickListener(){
        return onLongClickListener;
    }


    public interface OnLongClickListener {
        public void onLongClick(ViewHolder item, int position);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        static TextView sportsName;
        TextView date;
        static TextView idd;
        ImageView stadium;
        LinearLayout container;
        private AdapterView.OnItemClickListener mItemClickListener;


        ViewHolder(final View itemView) {
            super(itemView);
            System.out.println("qqqqq");
            cv = (CardView) itemView.findViewById(R.id.cv);
            sportsName = (TextView) itemView.findViewById(R.id.sports_name);
            
//            personAge = (TextView) itemView.findViewById(R.id.person_age);
            //    personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            idd = (TextView) itemView.findViewById(R.id.idd);
            container = (LinearLayout) itemView.findViewById(R.id.lol);
            System.out.println("qqqqqqqqqqqqq");

		}

    }
}  