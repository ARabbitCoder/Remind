package mr.liu.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import mr.liu.remind.R;

public class ExpandView extends FrameLayout{
	private Animation expand;
	private Animation closed;
	private boolean isExpand;
	public ExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initExpand();
	}

	public ExpandView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initExpand();
	}

	public ExpandView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initExpand();
	}
	
	private void initExpand() {
		expand = AnimationUtils.loadAnimation(getContext(), R.anim.choose_expand);
		expand.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				 setVisibility(View.VISIBLE);
			}
		});
		closed = AnimationUtils.loadAnimation(getContext(), R.anim.choose_closed);
		closed.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				setVisibility(View.INVISIBLE);
			}
		});
		setContentView();
	}
    public void collapse() {
        if (isExpand) {
            isExpand = false;
            clearAnimation();
            startAnimation(closed);
        }
    }
    
    public void expand() {
        if (!isExpand) {
            isExpand = true;
            clearAnimation();
            startAnimation(expand);
        }
    }
    public void setContentView(){
        View  view = LayoutInflater.from(getContext()).inflate(R.layout.expand_layout, null);
        removeAllViews();
        addView(view);
    }
    
}
