(ns secret-santa.routes.wishlist
  (:require [compojure.core :refer :all]
            [clostache.parser :as clo]
            [secret-santa.views.layout :as layout]
            [secret-santa.models.wishlist :as wishlist]
            [secret-santa.models.user :as user]
            [noir.response :as resp]
            [noir.session :as session]))

(defn wishlist []
  (let [my-wishlist (wishlist/get-wishlist-by-user (session/get :user))
        child-wishlist (wishlist/get-child-wishlist-by-user (session/get :user))]
    (layout/common (clo/render-resource "templates/wishlist.mustache" {:my my-wishlist :child child-wishlist}))))

(defn create-wishlist [text]
  (let [user (user/find-user-by-email (session/get :user))
        user-id (:_id user)])
    (wishlist/new-wishlist {:text text :user_id user-id})
    (resp/redirect "/wishlist"))

(defroutes wishlist-routes
  (GET "/wishlist" [] (wishlist))
  (POST "/wishlist" [text] (create-wishlist text)))
