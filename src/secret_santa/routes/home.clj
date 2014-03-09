(ns secret-santa.routes.home
  (:require [compojure.core :refer :all]
            [noir.util.route :refer [def-restricted-routes]]
            [noir.session :as session]
            [secret-santa.helpers.presenters :as pres]
            [secret-santa.models.wishlist :as wishlist]
            [secret-santa.models.user :as user]
            [secret-santa.views.layout :as layout]))

(defn- get-child-name [child-wishlist]
  (when-let [user-id (:user_id child-wishlist)]
    (:name (user/find-user-by-id user-id))))

(defn home []
  (let [my-wishlist (wishlist/get-wishlist-by-user (session/get :user))
        my-list (pres/md->html (:text my-wishlist)) 
        child-wishlist (wishlist/get-child-wishlist-by-user (session/get :user))
        child-text (:text child-wishlist)
        child-name (get-child-name child-wishlist)]
    (layout/render "templates/home.mustache" {:my {:text my-list} :child {:text child-text :name child-name}})))

(def-restricted-routes home-routes
  (GET "/" [] (home)))
