(ns qss4.button-buddy
  (:require [scad-clj.model :as m]
            [util.helpers :refer [render]]
            [util.math :refer [neg]]
            [util.model :refer [difference->>]]))

;; Configurable variables

; Mount Loop
(def mount-diameter 24)
(def mount-gap 1)
(def mount-thickness 3)
(def mount-width 10)

; Mount Nut and Bolt
(def flush-nut true)
(def bolt-head-diameter 7.5)
(def bolt-head-height 4)
(def bolt-stem-diameter 4.5)
(def nut-height 4)
(def nut-width 8.2)
(def bolt-padding 2)

; Stem
(def stem-width 15)
(def stem-height 30)

; Body
(def body-width 80)
(def body-height 30)
(def button-count 2)

;; Below is not configurable unless you understand what you are doing
(def handlebar-diameter (+ mount-gap mount-diameter))
(def handlebar-rad (/ handlebar-diameter 2))
(def mount-rad (+ handlebar-rad mount-thickness))
(def bolt-stem-rad (/ bolt-stem-diameter 2))
(def bolt-head-rad (/ bolt-head-diameter 2))

;; Parts - Mount

(def mount
  (->>
    (m/square stem-height stem-width :center false)
    (m/translate [0 (- mount-rad stem-width) 10])
    (m/union (m/circle mount-rad))
    (difference->>
      (m/circle handlebar-rad)
      (->>
        (m/square (+ stem-height handlebar-diameter mount-thickness) mount-gap :center false)
        (m/translate [0 (+ (- mount-rad stem-width) bolt-head-height bolt-padding) 0])))
    (m/extrude-linear {:height 10 :center false})
    (m/rotate [Math/PI (/ Math/PI 2) 0])
    (m/translate [mount-width 0 stem-height])))

(def mount-bolt
  (->>
    (m/cylinder bolt-stem-rad bolt-padding :center false)
    (m/translate [0 0 bolt-head-height])
    (m/union (m/cylinder bolt-head-rad bolt-head-height :center false))
    (m/rotate [(/ Math/PI 2) 0 0])
    (m/translate [(/ mount-width 2)
                  (->
                    (neg mount-rad)
                    (+ stem-width))
                  (->
                    (- mount-width bolt-head-diameter)
                    (/ 2)
                    (+ bolt-head-rad))])))

(def mount-nut
  (let [extra-height (- stem-width bolt-head-height bolt-padding 
                        mount-gap bolt-padding nut-height)
        nut-height (+ nut-height (if flush-nut 0 extra-height))
        bolt-padding (+ bolt-padding (if flush-nut extra-height 0))]
    (->>
      (m/cylinder bolt-stem-rad bolt-padding :center false)
      (m/translate [0 0 (neg bolt-padding)])
      (m/union
        (->>
          (let [a (/ nut-width 2)
                r (-> 3 Math/sqrt (/ 2) (* a))
                -r (neg r)
                -a (neg a)]
            (m/polygon
              [[0 -a]
               [r (/ -a 2)]
               [r (/ a 2)]
               [0 a]
               [-r (/ a 2)]
               [-r (/ -a 2)]]))
          (m/extrude-linear {:height nut-height :center false})))
      (m/rotate [(/ Math/PI 2) 0 0])
      (m/translate [(/ mount-width 2)
                    (->
                      (neg mount-rad)
                      (+ nut-height))
                    (->
                      (- mount-width bolt-head-diameter)
                      (/ 2)
                      (+ bolt-head-rad))]))))

(def model-mount
  (m/difference mount
                mount-bolt mount-nut))

(render model-mount)

(- 1
   (+ 1 1))
