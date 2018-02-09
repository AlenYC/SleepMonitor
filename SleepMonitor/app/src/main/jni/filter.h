/*
 * File: filter.h
 *
 * MATLAB Coder version            : 3.3
 * C/C++ source code generated on  : 31-Jan-2018 16:48:49
 */

#ifndef FILTER_H
#define FILTER_H

/* Include Files */
#include <math.h>
#include <stddef.h>
#include <stdlib.h>
#include <string.h>
#include "rt_nonfinite.h"
#include "rtwtypes.h"
#include "detect0131_types.h"

/* Function Declarations */
extern void b_filter(double b[5], double a[5], const double x[30000], double y
                     [30000]);
extern void filter(double b[7], double a[7], const double x[30000], double y
                   [30000]);

#endif

/*
 * File trailer for filter.h
 *
 * [EOF]
 */
