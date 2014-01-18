(ns secret-santa.routes.auth
  (:require [secret-santa.models.user :as user]
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

(defn create-user [email password fullname]
  (let [user (user/create-user email password fullname)]
    (session/put! :user (:_id user))
    (resp/redirect "/")))

(defn authenticate-user [email password]
  (let [user (user/find-user-by-email email)
        digest (:digest user)]
    (if (and user (crypt/compare password digest))
      (do
        (session/put! :user (:_id user))
        (resp/redirect "/"))
      (do
        (session/flash-put! :error password)
        (show-login-page)))))

(defn logout []
  (do
    (session/clear!)
    (resp/redirect "/login")))

(defroutes auth-routes
  (GET "/register" [] (show-registration-page))
  (POST "/register" [email password fullname] (create-user email password fullname))
  (GET "/login" [] (show-login-page))
  (POST "/login" [email password] (authenticate-user email password))
  (GET "/logout" [] (restricted (logout))))
