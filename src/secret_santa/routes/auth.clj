(ns secret-santa.routes.auth
  (:require [secret-santa.models.user :as user]
            [secret-santa.views.layout :as layout]
            [compojure.core :refer :all]
            [noir.session :as session]
            [noir.util.crypt :as crypt]
            [noir.response :as resp]))

(defn show-registration-page [& errors]
  (layout/render "templates/register.mustache" {:errors errors}))

(defn show-login-page [& errors]
  (layout/render "templates/register.mustache" {:errors errors}))

(defn create-user [email password]
  (let [user (user/create-user email password)]
    (session/put! :user (:_id user))
    (resp/redirect "/")))

(defn authenticate-user [email password]
  (let [user (user/find-user-by-email email)
        digest (:digest user)]
    (if (and (user) (crypt/compare password digest))
      (do
        (session/put! :user (:_id user))
        (resp/redirect "/"))
      (show-login-page {:errors ["Incorrect email or password"]}))))

(defn logout []
  (do
    (session/clear!)
    (resp/redirect "/login")))

(defroutes auth-routes
  (GET "/register" [] (show-registration-page))
  (POST "/register" [email password] (create-user email password))
  (GET "/login" [] (show-login-page))
  (POST "/login" [email password] (authenticate-user email password))
  (GET "/logout" [] (logout)))
