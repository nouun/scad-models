(ns util.helpers
  (:require [scad-clj.scad :refer [write-scad]]
            [scad-clj.model :as m]
            [clojure.java.io :refer [make-parents]]))

(defn render [model & {:keys [name fn] :or {name "render" fn 16}}]
  (let [file(str "models/" name ".scad")]
    (make-parents file)
    (spit file (write-scad
                 (m/fn! fn)
                 model))))

(defn render-high [model & {:keys [name] :or {name "render"}}]
  (render model :name name :fn 256))

(defn <- [f & args]
  (let [args-vec (vec args)]
    (apply f (peek args-vec) (pop args-vec))))

(defmacro when->> [cond & body]
  (let [arg (last body)
        body (butlast body)]
    `(let [arg# ~arg]
       (if ~cond
         (->> arg# ~@body)
         arg#))))
