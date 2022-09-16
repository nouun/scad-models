(ns util.model
  (:require [scad-clj.model :as m])
  (:require [util.helpers :refer [<-]]))

(defn difference->> [& block]
  (apply <- m/difference block))
