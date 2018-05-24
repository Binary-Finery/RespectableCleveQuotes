package spencerstudios.com.respectableclevequotes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import spencerstudios.com.respectableclevequotes.R;

public class RvQuotesAdapter extends RecyclerView.Adapter<RvQuotesAdapter.ViewHolder> {

    private List<String> quotes;
    /* add the following var and assign it a value of -1 */
    private int lastPosition = -1;
    private Context context;

    /* pass context to the adapters constructor */
    public RvQuotesAdapter(List<String> quotes, Context context){
        this.quotes = quotes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvQuote.setText(quotes.get(position));

        /* load the animation and fire it... */
        Animation animation = AnimationUtils.loadAnimation(context, (holder.getAdapterPosition() > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);

        /* assign current adapter position to the 'lastPosition' var */
        lastPosition = holder.getAdapterPosition();

        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quote = quotes.get(holder.getAdapterPosition());
                shareQuote(quote);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    /* override the following method and add the code... */
    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuote;
        ImageView ivShare;

        ViewHolder(View v) {
            super(v);

            tvQuote = v.findViewById(R.id.tv_quote);
            ivShare = v.findViewById(R.id.iv_share);
        }
    }

    private void shareQuote(String string){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, string);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
}
