(ns secret-santa.views.layout
  (:require [clostache.parser :as clo]
            [noir.session :as session]))

(defn common [body]
    (clo/render-resource "templates/layout.mustache"
                         {:body body :user (session/get :user) :error (get-errors) :success (get-success)}))

(defn render [template data]
  (common (clo/render-resource template data)))

(defn get-errors []
  (when-let [msg (session/flash-get :error)]
    {:text msg}))

(defn get-success []
  (when-let [msg (session/flash-get :success)]
    {:text msg}))
