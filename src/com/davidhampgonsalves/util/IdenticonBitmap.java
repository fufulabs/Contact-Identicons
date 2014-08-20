package com.davidhampgonsalves.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class IdenticonBitmap {
  private static volatile Matrix _scaledMatrix;

  public static Bitmap createScaledBitmap(Bitmap source, int dstWidth, int dstHeight, boolean filter) {
    Matrix matrix;
    synchronized (IdenticonBitmap.class) {
      matrix = _scaledMatrix;
      _scaledMatrix = null;
    }

    if (matrix == null) {
      matrix = new Matrix();
    }

    final int width = source.getWidth();
    final int height = source.getHeight();
    final float sx = dstWidth / (float) width;
    final float sy = dstHeight / (float) height;
    matrix.setScale(sx, sy);
    Bitmap output = Bitmap.createBitmap(source, 0, 0, width, height, matrix, filter);
    synchronized (IdenticonBitmap.class) {
      if (_scaledMatrix == null) {
        _scaledMatrix = matrix;
      }
    }
    if (output != source) {
      source.recycle();
    }
    return output;
  }
}
