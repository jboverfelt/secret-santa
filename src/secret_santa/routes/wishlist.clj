(ns secret-santa.routes.wishlist
  (:require [compojure.core :refer :all]
            [clostache.parser :as clo]
            [secret-santa.views.layout :as layout]
            [secret-santa.models.wishlist :as wishlist]
            [secret-santa.models.user :as user]
            [noir.response :as resp]
            [noir.session :as session]))

(defn show-wishlist []
  (let [my-wishlist (wishlist/get-wishlist-by-user (session/get :user))
        child-wishlist (wishlist/get-child-wishlist-by-user (session/get :user))]
    (layout/render "templates/wishlist.mustache" {:my my-wishlist :child child-wishlist})))

(defn create-wishlist [text]
  (let [user (user/find-user-by-email (session/get :user))
        user-id (:_id user)]
    (wishlist/new-wishlist {:text text :user_id user-id})
    (resp/redirect "/wishlist")))

(defn edit-wishlist []
  (let [wishlist (wishlist/get-wishlist-by-user (session/get :user))]
    (layout/render "templates/edit-wishlist.mustache" {:my wishlist})))

(defn update-wishlist [id text]
  (let [wishlist (wishlist/get-wishlist-by-id id)
        updated (assoc wishlist :text text)]
    (wishlist/update-wishlist updated)
    (resp/redirect "/wishlist")))

(defroutes wishlist-routes
  (GET "/wishlist" [] (show-wishlist))
  (GET "/wishlist/edit" [] (edit-wishlist))
  (POST "/wishlist/:id" [id text] (update-wishlist id text))
  (POST "/wishlist" [text] (create-wishlist text)))
