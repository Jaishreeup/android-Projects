package witty.wittybus.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import witty.wittybus.R;

public class RVAdapter1 extends RecyclerView.Adapter<RVAdapter1.PersonViewHolder>{

    public static ArrayList<String[]> routes;
    public static Context ctx;
    public int lastPosition = -1;
    public View.OnLongClickListener onLongClickListener;
    public RVAdapter1(ArrayList<String[]> routes, Context context)
    {
        this.routes = routes;
        ctx=context;
    }

    public void add(String s[],int position)
    {
        position = position == lastPosition ? getItemCount()  : position;
        routes.add(s);
        notifyItemInserted(position);
    }

    public void remove(int position)
    {
        if (position == lastPosition && getItemCount()>0)
            position = getItemCount() -1 ;

        if (position > lastPosition && position < getItemCount()) {
            routes.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card1, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        pvh.itemView.setTag(pvh);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i)
    {
        String str[]=routes.get(i);
        personViewHolder.bid.setText(str[0]);
        if(Integer.parseInt(str[1])<0)
        {
            personViewHolder.avail.setText("0");
            personViewHolder.extra.setText(""+(Integer.parseInt(str[1])*-1));
            personViewHolder.time.setText(str[2]);
        }
        else
        {
            personViewHolder.avail.setText(" "+str[1]);
            personViewHolder.extra.setText("0");
            personViewHolder.time.setText(str[2]);
        }
        setAnimation(personViewHolder.container, i);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return routes.size();
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        onLongClickListener = onLongClickListener;
    }
    public View.OnLongClickListener getOnItemClickListener(){
        return onLongClickListener;
    }


    public interface OnLongClickListener {
        public void onLongClick(PersonViewHolder item, int position);

    }

    public class PersonViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv;
        TextView bid,avail,extra,time;
        LinearLayout container;
        private AdapterView.OnItemClickListener mItemClickListener;
        PersonViewHolder(final View itemView)
        {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            bid = (TextView) itemView.findViewById(R.id.bid);
            time = (TextView) itemView.findViewById(R.id.time);
            avail = (TextView) itemView.findViewById(R.id.avail);
            extra = (TextView) itemView.findViewById(R.id.extra);
            container = (LinearLayout) itemView.findViewById(R.id.ll);
        }

    }

}
