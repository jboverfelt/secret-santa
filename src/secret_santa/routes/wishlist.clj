(ns secret-santa.routes.wishlist
  (:require [compojure.core :refer :all]
            [secret-santa.views.layout :as layout]
            [secret-santa.models.wishlist :as wishlist]
            [secret-santa.models.user :as user]
            [noir.util.route :refer [def-restricted-routes]]
            [noir.response :as resp]
            [noir.session :as session]))

(defn show-wishlist []
  (let [my-wishlist (wishlist/get-wishlist-by-user (session/get :user))
        child-wishlist (wishlist/get-child-wishlist-by-user (session/get :user))]
    (layout/render "templates/wishlist.mustache" {:my my-wishlist :child child-wishlist})))

(defn create-wishlist [text]
  (let [user-id (session/get :user)]
    (wishlist/new-wishlist {:text text :user_id user-id})
    (resp/redirect "/wishlist")))

(defn edit-wishlist []
  (let [wishlist (wishlist/get-wishlist-by-user (session/get :user))]
    (layout/render "templates/edit-wishlist.mustache" {:my wishlist})))

(defn update-wishlist [text]
  (let [wishlist (wishlist/get-wishlist-by-user (session/get :user))
        updated (assoc wishlist :text text)]
    (println (pr wishlist))
    (println (pr updated))
    (wishlist/update-wishlist updated)
    (resp/redirect "/wishlist")))


(def-restricted-routes wishlist-routes
  (GET "/wishlist" [] (show-wishlist))
  (POST "/wishlist" [text] (create-wishlist text))
  (GET "/wishlist/edit" [] (edit-wishlist))
  (POST "/wishlist/edit" [text] (update-wishlist text)))

