/*
 * File: mean.c
 *
 * MATLAB Coder version            : 3.3
 * C/C++ source code generated on  : 31-Jan-2018 16:48:49
 */

/* Include Files */
#include "rt_nonfinite.h"
#include "detect0131.h"
#include "mean.h"

/* Function Definitions */

/*
 * Arguments    : const double x[30000]
 * Return Type  : double
 */
double mean(const double x[30000])
{
  double y;
  int k;
  y = x[0];
  for (k = 0; k < 29999; k++) {
    y += x[k + 1];
  }

  y /= 30000.0;
  return y;
}

/*
 * File trailer for mean.c
 *
 * [EOF]
 */
