package %(package_name)s;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;
import android.opengl.GLSurfaceView;
import java.nio.ShortBuffer;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class %(renderer_class)s implements GLSurfaceView.Renderer {
  private static final String LOG_TAG = %(renderer_class)s.class.getSimpleName();

  private ShortBuffer _indexBuffer;
  private FloatBuffer _vertexBuffer;
  private int _nrOfVertices = 2;

  private void initialize() {
    /* Initialize stuff for the line */
    ByteBuffer vbb = ByteBuffer.allocateDirect(_nrOfVertices * 3 * 4);
    vbb.order(ByteOrder.nativeOrder());
    _vertexBuffer = vbb.asFloatBuffer();

    ByteBuffer ibb = ByteBuffer.allocateDirect(_nrOfVertices * 2);
    ibb.order(ByteOrder.nativeOrder());
    _indexBuffer = ibb.asShortBuffer();

    short[] indices = {0, 1};
    _indexBuffer.put(indices);
    _indexBuffer.position(0);
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    initialize();
  }

  private int _displaywidth, _displayheight;

  @Override
  public void onSurfaceChanged(GL10 gl, int w, int h) {
    gl.glViewport(0, 0, w, h);
    _displaywidth = w;
    _displayheight = h;
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    gl.glClearColor(0f, .5f, .5f, 1.0f);
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    gl.glLoadIdentity();
    gl.glScalef(1f / _displaywidth, 1f / _displayheight, 1f);

    /* Draw the line representing the two pointer positions */
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
    gl.glColor4f(1f, 1f, 1f, 1f);
    gl.glDrawElements(GL10.GL_LINES, _nrOfVertices, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
  }

  public void setPointer(int id, float x, float y) {
    if (id < 0 || id > 1)
      return;

    float[] coords = {
      0f, 0f, 0f,
      0f, 0f, 0f
    };

    _vertexBuffer.get(coords);
    _vertexBuffer.position(0);

    coords[id * 3 + 0] = (x * 2 - 1f) * _displaywidth;
    coords[id * 3 + 1] = (-y * 2 + 1f) * _displayheight;

    _vertexBuffer.put(coords);
    _vertexBuffer.position(0);
  }
}

