/*
 * File: filter.c
 *
 * MATLAB Coder version            : 3.3
 * C/C++ source code generated on  : 31-Jan-2018 16:48:49
 */

/* Include Files */
#include "rt_nonfinite.h"
#include "detect0131.h"
#include "filter.h"

/* Function Definitions */

/*
 * Arguments    : double b[5]
 *                double a[5]
 *                const double x[30000]
 *                double y[30000]
 * Return Type  : void
 */
void b_filter(double b[5], double a[5], const double x[30000], double y[30000])
{
  double a1;
  int k;
  int naxpy;
  int j;
  a1 = a[0];
  if ((!rtIsInf(a[0])) && (!rtIsNaN(a[0])) && (!(a[0] == 0.0)) && (a[0] != 1.0))
  {
    for (k = 0; k < 5; k++) {
      b[k] /= a1;
    }

    for (k = 0; k < 4; k++) {
      a[k + 1] /= a1;
    }

    a[0] = 1.0;
  }

  memset(&y[0], 0, 30000U * sizeof(double));
  for (k = 0; k < 30000; k++) {
    naxpy = 30000 - k;
    if (!(naxpy < 5)) {
      naxpy = 5;
    }

    for (j = 0; j + 1 <= naxpy; j++) {
      y[k + j] += x[k] * b[j];
    }

    naxpy = 29999 - k;
    if (!(naxpy < 4)) {
      naxpy = 4;
    }

    a1 = -y[k];
    for (j = 1; j <= naxpy; j++) {
      y[k + j] += a1 * a[j];
    }
  }
}

/*
 * Arguments    : double b[7]
 *                double a[7]
 *                const double x[30000]
 *                double y[30000]
 * Return Type  : void
 */
void filter(double b[7], double a[7], const double x[30000], double y[30000])
{
  double a1;
  int k;
  int naxpy;
  int j;
  a1 = a[0];
  if ((!rtIsInf(a[0])) && (!rtIsNaN(a[0])) && (!(a[0] == 0.0)) && (a[0] != 1.0))
  {
    for (k = 0; k < 7; k++) {
      b[k] /= a1;
    }

    for (k = 0; k < 6; k++) {
      a[k + 1] /= a1;
    }

    a[0] = 1.0;
  }

  memset(&y[0], 0, 30000U * sizeof(double));
  for (k = 0; k < 30000; k++) {
    naxpy = 30000 - k;
    if (!(naxpy < 7)) {
      naxpy = 7;
    }

    for (j = 0; j + 1 <= naxpy; j++) {
      y[k + j] += x[k] * b[j];
    }

    naxpy = 29999 - k;
    if (!(naxpy < 6)) {
      naxpy = 6;
    }

    a1 = -y[k];
    for (j = 1; j <= naxpy; j++) {
      y[k + j] += a1 * a[j];
    }
  }
}

/*
 * File trailer for filter.c
 *
 * [EOF]
 */
