package com.hexing.mvpdemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hexing.mvpdemo.R;
import com.hexing.mvpdemo.model.bean.PictureBean;

import java.util.List;

/**
 * @author by HEC271
 *         on 2017/12/28.
 */

public class PictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PictureBean> gankList;

    private Context mContext;

    private final int TYPE_FOOTER = 1;
    private final int TYPE_NORMAL = 0;

    private Boolean isShowFooter = true;

    public Boolean getShowFooter() {
        return isShowFooter;
    }

    public void setShowFooter(Boolean showFooter) {
        isShowFooter = showFooter;
    }

    public PictureAdapter(List<PictureBean> ganks, Context mContext) {
        this.gankList = ganks;
        this.mContext = mContext;
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //实现点击
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

        void loadMore();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            ItemViewHolder holder = new ItemViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.shop_item, parent, false));
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View footerview = LayoutInflater.from(mContext).inflate(R.layout.footerview, parent, false);
            return new FooterViewHolder(footerview);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
           // FrescoUtils.loadImage(Uri.parse(gankList.get(position).getUrl()), ((ItemViewHolder) holder).imgHead);
            ((ItemViewHolder) holder).imgHead.setImageURI(Uri.parse(gankList.get(position).getUrl()));
            ((ItemViewHolder) holder).reportshopname.setText(gankList.get(position).getCreatedAt());
            ((ItemViewHolder) holder).reportnums.setText(gankList.get(position).getPublishedAt());
            ((ItemViewHolder) holder).address.setText(gankList.get(position).getType());
            ((ItemViewHolder) holder).distance.setText(gankList.get(position).getWho());

        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).textFooter.setText("加载中...");
            ((FooterViewHolder) holder).textFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.loadMore();
                }
            });
        }

        //点击事件回调
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getAdapterPosition();
                    listener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        int begin = isShowFooter ? 1 : 0;
        if (gankList == null) {
            return begin;
        }
        return gankList.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {

        if (!isShowFooter) {
            return TYPE_NORMAL;
        }

        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView reportshopname;
        TextView reportnums;
        TextView address;
        TextView distance;
        SimpleDraweeView imgHead;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgHead = (SimpleDraweeView) itemView.findViewById(R.id.imgHead);
            reportshopname = (TextView) itemView.findViewById(R.id.textReportShopName);
            reportnums = (TextView) itemView.findViewById(R.id.textReportNums);
            address = (TextView) itemView.findViewById(R.id.textReportShopAddress);
            distance = (TextView) itemView.findViewById(R.id.textDistance);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView textFooter;

        public FooterViewHolder(View itemView) {
            super(itemView);
            textFooter = (TextView) itemView.findViewById(R.id.textFooter);
        }
    }

}
