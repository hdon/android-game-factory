package %(package_name)s;

import android.app.Activity;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.content.Context;
import android.view.MotionEvent;

public class %(view_class)s extends GLSurfaceView {
  private static final String LOG_TAG = %(view_class)s.class.getSimpleName();
  private %(renderer_class)s _renderer;

  public %(view_class)s(Context context) {
    super(context);
    _renderer = new %(renderer_class)s();
    setRenderer(_renderer);
  }

  public boolean onTouchEvent(final MotionEvent event) {
    int action = event.getAction();
    if (action == MotionEvent.ACTION_MOVE) {
      queueEvent(new Runnable() {
        public void run() {
          for (int i = 0, l = event.getPointerCount(); i < l; i++)
            _renderer.setPointer(event.getPointerId(i), event.getX(i) / getWidth(), event.getY(i) / getHeight());
        }
      });
    }
    return true;
  }
}

