(ns secret-santa.routes.admin
  (:require [compojure.core :refer :all]
            [secret-santa.views.layout :as layout]
            [secret-santa.models.user :as user]
            [noir.util.route :refer [def-restricted-routes]]
            [noir.response :as resp]))

(defn show-admin []
  (layout/render "templates/admin.mustache" {}))


(def-restricted-routes admin-routes
  (GET "/admin" [] (show-admin)))
