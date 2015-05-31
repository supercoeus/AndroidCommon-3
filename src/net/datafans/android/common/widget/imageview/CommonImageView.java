package net.datafans.android.common.widget.imageview;

import net.datafans.android.common.config.AndroidCommon;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CommonImageView extends FrameLayout {

	private ImageViewAdapter adapter;

	public CommonImageView(Context context) {
		super(context);
	}

	public CommonImageView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);

		ImageViewType type = getImageViewType();
		switch (type) {
		case Cube:
			adapter = new CubeImageViewAdapter(context);
			break;
		case Smart:
			adapter = new SmartImageViewAdapter(context);
			break;
		default:
			break;
		}

		addView(adapter.getImageView());
	}

	public void loadImage(String url) {
		adapter.loadImage(url);
	}

	private ImageViewType getImageViewType() {
		ImageViewType type = AndroidCommon.getImageViewType();
		if (type == null) {
			type = ImageViewType.Cube;
		}
		return type;
	}

}
