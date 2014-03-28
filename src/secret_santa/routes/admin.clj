(ns secret-santa.routes.admin
  (:require [compojure.core :refer :all]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [secret-santa.views.layout :as layout]
            [secret-santa.models.invited-user :as invite]
            [secret-santa.models.user :as user]
            [secret-santa.helpers.children :as children]
            [clojurewerkz.mailer.core :as mail]
            [clojure.string :as string]
            [noir.session :as session]
            [noir.util.route :refer [def-restricted-routes]]
            [noir.response :as resp]))

(defn- create-santa-child-map [user]
  (let [santa (:name user)
        child (user/find-child-name-by-id (:_id user))]
    {:santa santa :child child}))

(defn show-admin []
  (let [users (user/all-users)
        mappings (map create-santa-child-map users)]
    (layout/render "templates/admin.mustache" {:mappings mappings :token (anti-forgery-field)})))

(defn send-email [to]
  (mail/with-settings {:host "localhost" :port 1025}
    (mail/with-delivery-mode :smtp
      (mail/deliver-email {:from "admin" :to [to] :subject "Welcome to Secret Santa!"}
                          "email/templates/invite.html.mustache" {:url "http://localhost:8081/register"} :text/html))))

(defn setup-new-user [email]
  (invite/create-invited-user email)
  (send-email email))

(defn assign-children []
  (let [user-ids (map :_id (user/all-users))
        size (count user-ids)]
    (if (even? size)
      (do
        (user/update-children (children/create-santa-map user-ids))
        (session/flash-put! :success "Children assigned")
        (resp/redirect "/admin"))
      (do
        (session/flash-put! :error "You must have an even number of users to play")
        (resp/redirect "/admin")))))

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
