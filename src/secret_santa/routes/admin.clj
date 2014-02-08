(ns secret-santa.routes.admin
  (:require [compojure.core :refer :all]
            [secret-santa.views.layout :as layout]
            [secret-santa.models.invited-user :as invite]
            [secret-santa.models.user :as user]
            [clojure.string :as string]
            [noir.session :as session]
            [noir.util.route :refer [def-restricted-routes]]
            [noir.response :as resp]))

(defn show-admin []
  (layout/render "templates/admin.mustache" {}))

(defn setup-new-user [email]
  (invite/create-invited-user email))

(defn assign-children [])

(defn parse-emails [emails]
  (map string/trim (string/split emails #",")))

(defn invite-users [emails]
  (let [users (parse-emails emails)]
    (doall (map setup-new-user users))
    (session/flash-put! :success "Invites sent")
    (resp/redirect "/admin")))

(def-restricted-routes admin-routes
  (GET "/admin" [] (show-admin))
  (POST "/admin/assign" [] (assign-children))
  (POST "/admin/invite" [emails] (invite-users emails)))
