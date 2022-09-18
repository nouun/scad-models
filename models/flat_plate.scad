$fn = 128;
union () {
  difference () {
    union () {
      hull () {
        translate ([0, 0, 1.4]) {
          translate ([-11.7, -28.95, 0]) {
            cylinder (h=1, r=17.2);
          }
        }
        translate ([-11.7, -28.95, 0]) {
          cylinder (h=2.4, r=15.2);
        }
      }
      hull () {
        translate ([0, 0, 1.4]) {
          translate ([0, 17.25, 0]) {
            cylinder (h=1, r=28.9);
          }
        }
        translate ([0, 17.25, 0]) {
          cylinder (h=2.4, r=26.9);
        }
      }
      hull () {
        translate ([0, 0, 1.4]) {
          linear_extrude (height=1){
            translate ([-28.9, -28.95, 0]) {
              square ([34.4, 46.199999999999996]);
            }
          }
        }
        linear_extrude (height=2.4){
          translate ([-26.9, -28.95, 0]) {
            square ([30.4, 46.199999999999996]);
          }
        }
      }
    }
    translate ([-11.7, -28.95, 0]) {
      rotate ([0.0,0.0,-45.0]) {
        translate ([13.0, 0, 0]) {
          cylinder (h=6, r=2.2);
        }
      }
    }
    translate ([0, 17.25, 0]) {
      rotate ([0.0,0.0,173.0]) {
        translate ([24.7, 0, 0]) {
          cylinder (h=6, r=2.2);
        }
      }
    }
    translate ([0, 17.25, 0]) {
      rotate ([0.0,0.0,0.0]) {
        translate ([24.7, 0, 0]) {
          cylinder (h=6, r=2.2);
        }
      }
    }
    translate ([0, 17.25, 0]) {
      rotate ([0.0,0.0,86.00000000000001]) {
        translate ([24.7, 0, 0]) {
          cylinder (h=6, r=2.2);
        }
      }
    }
    translate ([-24.7, -11.25, 0]) {
      cylinder (h=6, r=2.2);
    }
  }
  translate ([0, 0, 2.4]) {
    union () {
      translate ([-11.7, -28.95, 0]) {
        rotate ([0.0,0.0,0.0]) {
          translate ([13.0, 0, 0]) {
            union () {
              translate ([0, 0, 10]) {
                cylinder (h=1.6, r=1);
              }
              cylinder (h=10, r=1.4);
            }
          }
        }
      }
      translate ([-11.7, -28.95, 0]) {
        rotate ([0.0,0.0,225.0]) {
          translate ([13.0, 0, 0]) {
            union () {
              translate ([0, 0, 10]) {
                cylinder (h=1.6, r=1);
              }
              cylinder (h=10, r=1.4);
            }
          }
        }
      }
      translate ([0, 17.25, 0]) {
        rotate ([0.0,0.0,45.0]) {
          translate ([24.7, 0, 0]) {
            union () {
              translate ([0, 0, 10]) {
                cylinder (h=1.6, r=1);
              }
              cylinder (h=10, r=1.4);
            }
          }
        }
      }
      translate ([0, 17.25, 0]) {
        rotate ([0.0,0.0,140.0]) {
          translate ([24.7, 0, 0]) {
            union () {
              translate ([0, 0, 10]) {
                cylinder (h=1.6, r=1);
              }
              cylinder (h=10, r=1.4);
            }
          }
        }
      }
      intersection () {
        union () {
          translate ([-11.7, -28.95, 0]) {
            rotate ([0.0,0.0,-45.0]) {
              translate ([13.0, 0, 0]) {
                union () {
                  difference () {
                    cylinder (h=6, r=3);
                    cylinder (h=6, r=2.2);
                  }
                  translate ([0, 0, 4.4]) {
                    difference () {
                      cylinder (h=1.6, r=3);
                      cylinder (h=1.6, r=1.2);
                    }
                  }
                }
              }
            }
          }
          translate ([0, 17.25, 0]) {
            rotate ([0.0,0.0,173.0]) {
              translate ([24.7, 0, 0]) {
                union () {
                  difference () {
                    cylinder (h=6, r=3);
                    cylinder (h=6, r=2.2);
                  }
                  translate ([0, 0, 4.4]) {
                    difference () {
                      cylinder (h=1.6, r=3);
                      cylinder (h=1.6, r=1.2);
                    }
                  }
                }
              }
            }
          }
          translate ([0, 17.25, 0]) {
            rotate ([0.0,0.0,0.0]) {
              translate ([24.7, 0, 0]) {
                union () {
                  difference () {
                    cylinder (h=6, r=3);
                    cylinder (h=6, r=2.2);
                  }
                  translate ([0, 0, 4.4]) {
                    difference () {
                      cylinder (h=1.6, r=3);
                      cylinder (h=1.6, r=1.2);
                    }
                  }
                }
              }
            }
          }
          translate ([0, 17.25, 0]) {
            rotate ([0.0,0.0,86.00000000000001]) {
              translate ([24.7, 0, 0]) {
                union () {
                  difference () {
                    cylinder (h=6, r=3);
                    cylinder (h=6, r=2.2);
                  }
                  translate ([0, 0, 4.4]) {
                    difference () {
                      cylinder (h=1.6, r=3);
                      cylinder (h=1.6, r=1.2);
                    }
                  }
                }
              }
            }
          }
          translate ([-24.7, -11.25, 0]) {
            union () {
              difference () {
                cylinder (h=6, r=3);
                cylinder (h=6, r=2.2);
              }
              translate ([0, 0, 4.4]) {
                difference () {
                  cylinder (h=1.6, r=3);
                  cylinder (h=1.6, r=1.2);
                }
              }
            }
          }
        }
        union () {
          translate ([0, 17.25, 0]) {
            cylinder (h=6, r=26.74);
          }
          translate ([-11.7, -28.95, 0]) {
            cylinder (h=6, r=15.04);
          }
          linear_extrude (height=6){
            translate ([-26.74, -28.95, 0]) {
              square ([30.08, 46.199999999999996]);
            }
          }
        }
      }
    }
  }
  translate ([0, 0, 2.4]) {
    difference () {
      union () {
        translate ([0, 17.25, 0]) {
          cylinder (h=2, r=28.9);
        }
        translate ([-11.7, -28.95, 0]) {
          cylinder (h=2, r=17.2);
        }
        linear_extrude (height=2){
          translate ([-28.9, -28.95, 0]) {
            square ([34.4, 46.199999999999996]);
          }
        }
      }
      union () {
        translate ([0, 17.25, 0]) {
          cylinder (h=2, r=26.74);
        }
        translate ([-11.7, -28.95, 0]) {
          cylinder (h=2, r=15.04);
        }
        linear_extrude (height=2){
          translate ([-26.74, -28.95, 0]) {
            square ([30.08, 46.199999999999996]);
          }
        }
      }
      translate ([0, 0, 1.4]) {
        union () {
          translate ([0, 17.25, 0]) {
            cylinder (h=2, r=27.5);
          }
          translate ([-11.7, -28.95, 0]) {
            cylinder (h=2, r=15.799999999999999);
          }
          linear_extrude (height=2){
            translate ([-27.5, -28.95, 0]) {
              square ([31.599999999999998, 46.199999999999996]);
            }
          }
        }
      }
    }
  }
}
