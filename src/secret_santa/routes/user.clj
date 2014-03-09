(ns secret-santa.routes.user
  (:require [compojure.core :refer :all]
            [noir.util.route :refer [def-restricted-routes]]
            [noir.session :as session]
            [secret-santa.models.user :as user]
            [secret-santa.views.layout :as layout]))

(defn users []
  (let [names (vec (map :name (user/all-users)))]
    (layout/render "templates/users.mustache" {:names names})))

(def-restricted-routes user-routes
  (GET "/users" [] (users)))  
