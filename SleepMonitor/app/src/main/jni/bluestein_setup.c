/*
 * File: bluestein_setup.c
 *
 * MATLAB Coder version            : 3.3
 * C/C++ source code generated on  : 31-Jan-2018 16:48:49
 */

/* Include Files */
#include "rt_nonfinite.h"
#include "detect0131.h"
#include "bluestein_setup.h"

/* Function Definitions */

/*
 * Arguments    : creal_T wwc[59999]
 * Return Type  : void
 */
void bluestein_setup(creal_T wwc[59999])
{
  int idx;
  int rt;
  int k;
  int y;
  double nt_im;
  double nt_re;
  idx = 29998;
  rt = 0;
  wwc[29999].re = 1.0;
  wwc[29999].im = 0.0;
  for (k = 0; k < 29999; k++) {
    y = ((k + 1) << 1) - 1;
    if (60000 - rt <= y) {
      rt = (y + rt) - 60000;
    } else {
      rt += y;
    }

    nt_im = -3.1415926535897931 * (double)rt / 30000.0;
    if (nt_im == 0.0) {
      nt_re = 1.0;
      nt_im = 0.0;
    } else {
      nt_re = cos(nt_im);
      nt_im = sin(nt_im);
    }

    wwc[idx].re = nt_re;
    wwc[idx].im = -nt_im;
    idx--;
  }

  idx = 0;
  for (k = 29998; k >= 0; k += -1) {
    wwc[k + 30000] = wwc[idx];
    idx++;
  }
}

/*
 * File trailer for bluestein_setup.c
 *
 * [EOF]
 */
