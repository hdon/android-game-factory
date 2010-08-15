package %(package_name)s;

import android.app.Activity;
import android.os.Bundle;

public class %(activity_class)s extends Activity
{
  private static final String LOG_TAG = %(activity_class)s.class.getSimpleName();
  private %(view_class)s _view;

  /** Called when the activity is first created. */
  @Override
    public void onCreate(Bundle savedInstanceState)
    {
      super.onCreate(savedInstanceState);
      _view = new %(view_class)s(this);
      setContentView(_view);
    }
}
