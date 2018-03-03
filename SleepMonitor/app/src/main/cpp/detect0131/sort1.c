/*
 * File: sort1.c
 *
 * MATLAB Coder version            : 3.3
 * C/C++ source code generated on  : 31-Jan-2018 16:48:49
 */

/* Include Files */
#include "rt_nonfinite.h"
#include "detect0131.h"
#include "sort1.h"

/* Function Definitions */

/*
 * Arguments    : double x[3]
 * Return Type  : void
 */
void sort(double x[3])
{
  boolean_T p;
  double tmp;
  if ((x[0] <= x[1]) || rtIsNaN(x[1])) {
    p = true;
  } else {
    p = false;
  }

  if (p) {
    if ((x[1] <= x[2]) || rtIsNaN(x[2])) {
      p = true;
    } else {
      p = false;
    }

    if (!p) {
      if ((x[0] <= x[2]) || rtIsNaN(x[2])) {
        p = true;
      } else {
        p = false;
      }

      if (p) {
        tmp = x[1];
        x[1] = x[2];
        x[2] = tmp;
      } else {
        tmp = x[2];
        x[2] = x[1];
        x[1] = x[0];
        x[0] = tmp;
      }
    }
  } else {
    if ((x[0] <= x[2]) || rtIsNaN(x[2])) {
      p = true;
    } else {
      p = false;
    }

    if (p) {
      tmp = x[0];
      x[0] = x[1];
      x[1] = tmp;
    } else {
      if ((x[1] <= x[2]) || rtIsNaN(x[2])) {
        p = true;
      } else {
        p = false;
      }

      if (p) {
        tmp = x[0];
        x[0] = x[1];
        x[1] = x[2];
        x[2] = tmp;
      } else {
        tmp = x[0];
        x[0] = x[2];
        x[2] = tmp;
      }
    }
  }
}

/*
 * File trailer for sort1.c
 *
 * [EOF]
 */
