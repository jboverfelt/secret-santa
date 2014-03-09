(ns secret-santa.routes.wishlist
  (:require [compojure.core :refer :all]
            [secret-santa.views.layout :as layout]
            [secret-santa.models.wishlist :as wishlist]
            [secret-santa.models.user :as user]
            [noir.util.route :refer [def-restricted-routes]]
            [noir.response :as resp]
            [noir.session :as session]))

(defn- edit-wishlist-helper [wishlist]
  (wishlist/edit-wishlist wishlist)
  (session/flash-put! :success "Wishlist updated")
  (resp/redirect "/"))

(defn wishlist->json []
  (let [my-wishlist (wishlist/get-wishlist-by-user (session/get :user))
        text (:text my-wishlist)]
    (resp/json {:text text})))

(defn edit-wishlist []
  (let [wishlist (wishlist/get-wishlist-by-user (session/get :user))]
    (layout/render "templates/edit-wishlist.mustache" {:my wishlist})))

(defn update-wishlist [text]
  (if-let [wishlist (wishlist/get-wishlist-by-user (session/get :user))]
    (let [updated (assoc wishlist :text text)]
      (edit-wishlist-helper updated))
    (let [user-id (session/get :user)]
      (edit-wishlist-helper {:text text :user_id user-id}))))

(def-restricted-routes wishlist-routes
  (GET "/wishlist/json" [] (wishlist->json))
  (POST "/wishlist" [text] (update-wishlist text))
  (GET "/wishlist/edit" [] (edit-wishlist)))
