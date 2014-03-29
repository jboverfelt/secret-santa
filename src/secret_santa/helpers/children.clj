(ns secret-santa.helpers.children
  (:require [clojure.core.logic :as logic]))

; This implementation adapted from https://github.com/tie-rack/secret-santa

(logic/defne no-doubleso
  "pairs does not have a pair with identical members"
  [pairs]
  ([()])
  ([[[a b] . ps]]
    (logic/!= a b)
    (no-doubleso ps)))

(logic/defne zipo
  "zipped is zipped pairs of xl and yl"
  [xl yl zipped]
  ([() () ()])
  ([[x . xs] [y . ys] [z . zs]]
    (logic/== z [x y])
    (zipo xs ys zs)))

(defn- create-santa-pairs [ids]
  (logic/run 1 [q]
    (logic/fresh [recipients]
      (logic/permuteo ids recipients)
      (zipo ids recipients q)
      (no-doubleso q))))

(defn create-santa-map [ids]
  (->> (create-santa-pairs ids)
       first
       (into {})))
