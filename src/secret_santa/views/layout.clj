(ns secret-santa.views.layout
  (:require [clostache.parser :as clo]))

(defn common [body]
    (clo/render-resource "templates/layout.mustache" {:body body}))
