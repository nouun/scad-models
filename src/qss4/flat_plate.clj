(ns qss4.flat-plate
  (:require [scad-clj.model :as m]
            [util.helpers :refer [render render-high <- when->>]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Configurable variables
; Measuments
(def big-height 57.8)
(def small-height 34.4)
(def width 92.3)
(def thickness 2.16)

; Top Brim
(def brim
  {:height 2
   :top
   {:height 0.6
    :thickness 1.4}})

; Base
(def base
  {:offset 2
   :thickness 2.4})

; Standoffs
(def standoff
  {:cover
   {:width 6
    :height 6
    :thread-width 2.4
    :thickness 0.8
    :offset 1.2}
   :pcb
   {:width 4.4
    :height 6
    :offset 1.2
    :stand
    {:height 10
     :width 1.4}
    :inset
    {:height 1.6
     :width 1}}})


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Below is not configurable unless you understand what you are doing

(def offset
  {:big-y (- (/ width 2) (/ big-height 2))
   :small-y (-> (/ width 2)
                (-)
                (+ (/ small-height 2)))
   :small-x (-> (/ big-height 2)
                (-)
                (+ (/ small-height 2)))})


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Base Plate
; Big circle
(defn big [& {:keys [margin height] :or {margin 0 height 0}}]
  (->>
    (m/cylinder (-> big-height
                    (/ 2)
                    (- margin))
                height)
    (m/translate [0 (offset :big-y) 0])))

; Small circle
(defn small [& {:keys [margin height] :or {margin 0 height 0}}]
  (->>
    (m/cylinder (-> small-height
                    (/ 2)
                    (- margin))
                height)
    (m/translate [(offset :small-x) (offset :small-y) 0])))

; Join for two circles
(defn join [& {:keys [margin height] :or {margin 0 height 0}}]
  (let [width (- width (/ small-height 2) (/ big-height 2))]
    (->>
      (m/square (- small-height (* margin 2)) width)
      (m/translate [(-> (offset :small-x)
                      (- (/ small-height 2))
                      (+ margin))
                    (offset :small-y)
                    0])
      (m/extrude-linear {:height height}))))

; All parts in one
(defn base-plate [& {:keys [margin height] :or {margin 0 height (brim :height)}}]
  (m/union (big :margin margin :height height)
           (small :margin margin :height height)
           (join :margin margin :height height)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Standoff Helper

(defn standoff-translate [translations model]
  (let [[tx ty] (nth translations 0)]
    (->> model
      (when->> (>= (count translations) 3)
        (m/translate [(nth translations 2) 0 0]))
      (when->> (>= (count translations) 2)
        (m/rotate [0 0 (m/deg->rad (nth translations 1))]))
      (m/translate [tx ty 0]))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Cover Standoffs

(defn make-cover-standoffs [model]
  (let [cover-offset (-> ((standoff :cover) :width)
                         (/ 2)
                         (+ ((standoff :cover) :offset)))
        small-translate [(offset :small-x) (offset :small-y)]
        big-translate [0 (offset :big-y)]
        big-offset (-> (/ big-height 2)
                       (- cover-offset))]
    (map #(standoff-translate % model)
         [[small-translate -45 (-> (/ small-height 2)
                                   (- cover-offset))]
          [big-translate 173 big-offset]
          [big-translate   0 big-offset]
          [big-translate  86 big-offset]
          [[(-> (- big-height)
                (/ 2)
                (+ cover-offset))
            (-> (offset :small-y)
                (+ (/ small-height 2) 0.5))]]]))) ; + 1 too much, + 0 too little

(def cover-standoff-diffs
  (make-cover-standoffs
    (m/cylinder (-> ((standoff :cover) :width)
                    (/ 2)
                    (- ((standoff :cover) :thickness)))
                ((standoff :cover) :height))))

(def cover-standoffs
 (make-cover-standoffs
   (->>
     (m/cylinder (-> ((standoff :cover) :thread-width)
                     (/ 2))
                 (* 2 ((standoff :cover) :thickness))
                 :center false)
     (m/difference
       (m/cylinder (/ ((standoff :cover) :width) 2)
                   (* 2 ((standoff :cover) :thickness))
                   :center false))
     (m/translate [0 0 (- ((standoff :cover) :height)
                          (* 2 ((standoff :cover) :thickness)))])
     (m/union
       (m/difference
         (m/cylinder (/ ((standoff :cover) :width) 2)
                     ((standoff :cover) :height))
         (m/cylinder (-> ((standoff :cover) :width)
                         (/ 2)
                         (- ((standoff :cover) :thickness)))
                     ((standoff :cover) :height)))))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; PCB Standoffs

(def pcb-standoffs
  (let [cover-offset (-> ((standoff :cover) :width)
                         (/ 2)
                         (+ ((standoff :cover) :offset)))
        small-translate [(offset :small-x) (offset :small-y)]
        big-translate [0 (offset :big-y)]
        big-offset (-> (/ big-height 2)
                       (- cover-offset))
        small-offset (-> (/ small-height 2)
                         (- cover-offset))]
    (map #(->>
            (m/cylinder (((standoff :pcb) :stand) :width)
                        (((standoff :pcb) :stand) :height))
            (m/union
              (->>
                (m/cylinder (((standoff :pcb) :inset) :width)
                            (((standoff :pcb) :inset) :height))
                (m/translate [0 0 (((standoff :pcb) :stand) :height)])))
            (standoff-translate %))
         [[small-translate   0 small-offset]
          [small-translate 225 small-offset]
          [big-translate    45 big-offset]
          [big-translate   140 big-offset]])))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Base Bottom Chamfer

(defn hull-part [part]
  (->>
    (part :height 1)
    (m/translate [0 0 (- (base :thickness) 1)])
    (<- m/hull
      (part :height (base :thickness) :margin (base :offset)))))

(def base-cover
  (->>
    (base-plate :margin ((brim :top) :thickness))
    (m/translate [0 0 (- (brim :height)
                         ((brim :top) :height))])
    (m/difference
      (base-plate)
      (base-plate :margin thickness))
    (m/translate [0 0 (base :thickness)])
    (m/union
      (m/difference
        (apply m/union (map hull-part [small big join]))
        cover-standoff-diffs)
      (->>
        (m/union cover-standoffs)
        (<- m/intersection
            (base-plate :margin thickness :height ((standoff :cover) :height)))
        (m/union
          pcb-standoffs)
        (m/translate [0 0 (base :thickness)])))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Export to file

(render-high base-cover :name "flat_plate")


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Used for debugging

(def model
  base-cover)
