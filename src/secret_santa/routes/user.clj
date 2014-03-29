(ns secret-santa.routes.user
  (:require [compojure.core :refer :all]
            [noir.util.route :refer [def-restricted-routes]]
            [noir.session :as session]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [secret-santa.models.user :as user]
            [secret-santa.views.layout :as layout]))

(defn users []
  (let [names (vec (map :name (user/all-users)))]
    (layout/render "templates/users.mustache" {:names names})))

(defn settings []
  (layout/render "templates/profile.mustache" {:token (anti-forgery-field)}))

(def-restricted-routes user-routes
  (GET "/users" [] (users))
  (GET "/settings" [] (settings)))  
