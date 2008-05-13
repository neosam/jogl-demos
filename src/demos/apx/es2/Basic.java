package demos.apx;

import java.nio.*;
import javax.media.opengl.*;
import com.sun.opengl.impl.egl.*;

public class Basic {
    public static void main(String[] args) {
        System.out.println("Basic.main()");
        try {
            System.out.println("GLDrawableFactory.getFactory()");
            EGLDrawableFactory factory = (EGLDrawableFactory) GLDrawableFactory.getFactory();

            System.out.println("EGLDrawableFactory.initialize()");
            factory.initialize();
            System.out.println("factory.createExternalGLContext()");
            GLContext context = factory.createExternalGLContext();
            // OpenGL context is current at this point

            // The following is a no-op that is only needed to get the
            // Java-level GLContext object set up in thread-local storage
            System.out.println("context.makeCurrent()");
            context.makeCurrent();
            context.setGL(new DebugGL(context.getGL()));

            GL gl = context.getGL();

            Shader shader = Shader.createBinaryProgram(data_vert, GL.GL_NVIDIA_PLATFORM_BINARY_NV,
                                                       data_frag, GL.GL_NVIDIA_PLATFORM_BINARY_NV);
            shader.setAttribByName("pos_attr", 2, GL.GL_FLOAT, false, 0, FloatBuffer.wrap(vert));
            shader.setAttribByName("col_attr", 4, GL.GL_FLOAT, false, 0, FloatBuffer.wrap(col));

            float angle = 0;

            long startTime = System.currentTimeMillis();
            long curTime = 0;

            do {
                angle += 0.15f;
                gl.glClear(GL.GL_COLOR_BUFFER_BIT);
                // we only need to pass cos and sin to the shader.
                float rad = (float) Math.toRadians(angle);
                gl.glUniform2f(shader.getUniformLocation("rot"),
                               (float) Math.cos(rad),
                               (float) Math.sin(rad));

                gl.glDrawElements(GL.GL_TRIANGLES, 3, GL.GL_UNSIGNED_BYTE, indices);
    
                // FIXME -- need an external GLDrawable
                factory.swapBuffers();

                // Process events
                factory.processEvents();

                curTime = System.currentTimeMillis();
            } while ((curTime - startTime) < 15000);
        } catch (GLException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    static ByteBuffer indices = ByteBuffer.wrap(new byte[] { 0, 1, 2 });
    static final float ROOT_3_OVER_2 = 0.8660254f;
    static final float ROOT_3_OVER_6 = (ROOT_3_OVER_2/3.0f);
    static float[] vert = { 0.5f, -ROOT_3_OVER_6,
                            -0.5f, -ROOT_3_OVER_6,
                            0.0f,  ROOT_3_OVER_2 - ROOT_3_OVER_6 };
    static float[] col = { 1.0f, 0.0f, 0.0f, 1.0f,
                           0.0f, 1.0f, 0.0f, 1.0f,
                           0.0f, 0.0f, 1.0f, 1.0f};

    static byte[] data_vert = {
        (byte) 0x6a, (byte) 0xbe, (byte) 0x3a, (byte) 0x95, (byte) 0x1f, (byte) 0x6d, (byte) 0x83, (byte) 0x26, (byte) 0x05, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x11, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x49, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1e, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x20, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x51, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x18, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x57, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x5b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x5b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x1c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0d, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x62, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x64, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x20, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x11, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x20, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x20, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x13, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x74, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x58, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x58, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6d, (byte) 0x61, (byte) 0x6e, (byte) 0x46, (byte) 0x49, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x63, (byte) 0x6f, (byte) 0x6c, (byte) 0x5f, (byte) 0x61, (byte) 0x74, (byte) 0x74, (byte) 0x72, (byte) 0x00, (byte) 0x70, (byte) 0x6f, (byte) 0x73,
        (byte) 0x5f, (byte) 0x61, (byte) 0x74, (byte) 0x74, (byte) 0x72, (byte) 0x00, (byte) 0x63, (byte) 0x6f, (byte) 0x6c, (byte) 0x5f, (byte) 0x76, (byte) 0x61, (byte) 0x72, (byte) 0x00, (byte) 0x72, (byte) 0x6f,
        (byte) 0x74, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x52, (byte) 0x8b, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x09, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x50, (byte) 0x8b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1a, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x50, (byte) 0x8b, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x20, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x81, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x05, (byte) 0x42, (byte) 0x14, (byte) 0x00, (byte) 0x06, (byte) 0x22, (byte) 0x6c, (byte) 0x9c, (byte) 0x1f, (byte) 0x40, (byte) 0x0d, (byte) 0x01, (byte) 0x40, (byte) 0x00,
        (byte) 0x83, (byte) 0xc0, (byte) 0x06, (byte) 0x81, (byte) 0x9c, (byte) 0xff, (byte) 0x41, (byte) 0x60, (byte) 0x6c, (byte) 0x1c, (byte) 0x00, (byte) 0x00, (byte) 0x2a, (byte) 0x00, (byte) 0x80, (byte) 0x00,
        (byte) 0xc3, (byte) 0x00, (byte) 0x04, (byte) 0x81, (byte) 0xfc, (byte) 0x9f, (byte) 0x41, (byte) 0x60, (byte) 0x6c, (byte) 0x9c, (byte) 0x1f, (byte) 0x40, (byte) 0x00, (byte) 0x10, (byte) 0x40, (byte) 0x00,
        (byte) 0x83, (byte) 0xc0, (byte) 0x86, (byte) 0x01, (byte) 0x80, (byte) 0x5f, (byte) 0x40, (byte) 0x60, (byte) 0x6c, (byte) 0x9c, (byte) 0x1f, (byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
        (byte) 0xc0, (byte) 0x40, (byte) 0x15, (byte) 0x01, (byte) 0x80, (byte) 0x9f, (byte) 0x20, (byte) 0x00, (byte) 0x6c, (byte) 0x9c, (byte) 0x1f, (byte) 0x40, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
        (byte) 0xea, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x81, (byte) 0x1f, (byte) 0x21, (byte) 0xa0
    };

    static byte[] data_frag = {
        (byte) 0x62, (byte) 0x45, (byte) 0xed, (byte) 0x02, (byte) 0x2f, (byte) 0x6d, (byte) 0x83, (byte) 0x26, (byte) 0x05, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x11, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x49, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x4b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x4b, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x14, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0d, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0e, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x16, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x50, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x20, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x17, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x58, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0f, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x59, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x4c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x11, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x13, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x54, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x54, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x14, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x15, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6c, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x6d, (byte) 0x61, (byte) 0x6e, (byte) 0x46, (byte) 0x49, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x63, (byte) 0x6f, (byte) 0x6c, (byte) 0x5f, (byte) 0x76, (byte) 0x61, (byte) 0x72, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x84, (byte) 0x95, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xf1, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xf0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xf0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xf0,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xf0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xf0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xf0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xf0,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x07, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x15, (byte) 0x20, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x41, (byte) 0x52, (byte) 0x32, (byte) 0x30, (byte) 0x2d, (byte) 0x42, (byte) 0x49, (byte) 0x4e, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x01, (byte) 0x00, (byte) 0x41, (byte) 0x25, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x15, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x26, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x04, (byte) 0x26, (byte) 0xba, (byte) 0x51, (byte) 0x4e, (byte) 0x10,
        (byte) 0x04, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x27, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x28,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x06, (byte) 0x28, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x29,
        (byte) 0x05, (byte) 0x00, (byte) 0x02, (byte) 0x00
    };
}