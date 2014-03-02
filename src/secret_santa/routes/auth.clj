(ns secret-santa.routes.auth
  (:require [secret-santa.models.user :as user]
            [secret-santa.models.invited-user :as invite]
            [secret-santa.views.layout :as layout]
            [compojure.core :refer :all]
            [noir.util.route :refer [restricted]]
            [noir.session :as session]
            [noir.util.crypt :as crypt]
            [noir.response :as resp]))

(defn show-registration-page []
  (layout/render "templates/register.mustache" {}))

(defn show-login-page []
  (layout/render "templates/login.mustache" {}))

(defn invited? [email]
  (invite/find-invited-user-by-email email))

(defn create-user [email password fullname]
  (let [user (user/create-user email password fullname)]
    (session/put! :user (:_id user))
    (resp/redirect "/")))

(defn check-invite [email password fullname]
  (if (invited? email)
    (create-user email password fullname)
    (do
      (session/flash-put! :error "You must be invited by an administrator before registering")
      (show-registration-page))))

(defn authenticate-user [email password]
  (let [user (user/find-user-by-email email)
        digest (:digest user)]
    (if (and user (crypt/compare password digest))
      (do
        (session/put! :user (:_id user))
        (resp/redirect "/"))
      (do
        (session/flash-put! :error "Incorrect email or password.")
        (show-login-page)))))

(defn logout []
  (do
    (session/clear!)
    (session/flash-put! :success "You have logged out successfully")
    (resp/redirect "/login")))

(defroutes auth-routes
  (GET "/register" [] (show-registration-page))
  (POST "/register" [email password fullname] (check-invite email password fullname))
  (GET "/login" [] (show-login-page))
  (POST "/login" [email password] (authenticate-user email password))
  (GET "/logout" [] (logout)))
