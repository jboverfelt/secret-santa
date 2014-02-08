(ns secret-santa.views.layout
  (:require [clostache.parser :as clo]
            [noir.session :as session]
            [secret-santa.models.user :as user]))

(defn get-success []
  (when-let [msg (session/flash-get :success)]
    {:text msg}))

(defn get-errors []
  (when-let [msg (session/flash-get :error)]
    {:text msg}))

(defn get-warns []
  (when-let [msg (session/flash-get :warn)]
    {:text msg}))

(defn get-user-id []
  (session/get :user))

(defn get-email []
  (when-let [user-id (get-user-id)]
    (:email (user/find-user-by-id user-id))))

(defn get-admin []
  (when-let [user-id (get-user-id)]
    (:admin (user/find-user-by-id user-id))))

(defn common [body]
    (clo/render-resource "templates/layout.mustache"
                         {:body body :user (get-user-id) :email (get-email) :admin (get-admin)
                          :warn (get-warns) :error (get-errors) :success (get-success)}))

(defn render [template data]
  (common (clo/render-resource template data)))


