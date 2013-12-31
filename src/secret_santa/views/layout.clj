(ns secret-santa.views.layout
  (:require [clostache.parser :as clo]
            [noir.session :as session]))

(defn common [body]
    (clo/render-resource "templates/layout.mustache" {:body body :user (session/get :user)}))

(defn render [template data]
  (common (clo/render-resource template data)))
