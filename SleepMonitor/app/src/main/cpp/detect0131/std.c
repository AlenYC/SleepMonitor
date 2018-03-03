/*
 * File: std.c
 *
 * MATLAB Coder version            : 3.3
 * C/C++ source code generated on  : 31-Jan-2018 16:48:49
 */

/* Include Files */
#include "rt_nonfinite.h"
#include "detect0131.h"
#include "std.h"

/* Function Definitions */

/*
 * Arguments    : const double varargin_1[30000]
 * Return Type  : double
 */
double b_std(const double varargin_1[30000])
{
  double y;
  int ix;
  double xbar;
  int k;
  double r;
  ix = 0;
  xbar = varargin_1[0];
  for (k = 0; k < 29999; k++) {
    ix++;
    xbar += varargin_1[ix];
  }

  xbar /= 30000.0;
  ix = 0;
  r = varargin_1[0] - xbar;
  y = r * r;
  for (k = 0; k < 29999; k++) {
    ix++;
    r = varargin_1[ix] - xbar;
    y += r * r;
  }

  y /= 29999.0;
  return sqrt(y);
}

/*
 * File trailer for std.c
 *
 * [EOF]
 */
