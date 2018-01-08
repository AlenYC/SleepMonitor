package com.ycl.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ycl.framework.R;


/**
 * 进度条 webview
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

    protected ProgressBar pb;

    //xml中实现  AttributeSet
    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        pb = new ProgressBar(context.getApplicationContext(), null, android.R.attr.progressBarStyleHorizontal);
        pb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 6, 0, 0));
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.bg_progress_webview));
        addView(pb);
        setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {

        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            if (mHeader != null)
//                mHeader.setMiddleTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                pb.setVisibility(GONE);
            } else {
                if (pb.getVisibility() == GONE)
                    pb.setVisibility(VISIBLE);
                pb.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    //    @Override
    //    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    //        LayoutParams lp = (LayoutParams) pb.getLayoutParams();
    //        lp.x = l;
    //        lp.y = t;
    //        pb.setLayoutParams(lp);
    //        super.onScrollChanged(l, t, oldl, oldt);
    //    }
}
