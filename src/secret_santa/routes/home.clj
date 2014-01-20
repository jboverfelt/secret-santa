(ns secret-santa.routes.home
  (:require [compojure.core :refer :all]
            [clostache.parser :as clo]
            [noir.util.route :refer [def-restricted-routes]]
            [noir.session :as session]
            [secret-santa.models.wishlist :as wishlist]
            [secret-santa.helpers.presenters :as pres]
            [secret-santa.views.layout :as layout]))

(defn home []
  (let [my-wishlist (wishlist/get-wishlist-by-user (session/get :user))
        my-list (pres/text->html (:text my-wishlist))
        child-wishlist (wishlist/get-child-wishlist-by-user (session/get :user))]
    (layout/render "templates/home.mustache" {:my {:text my-list} :child child-wishlist})))

(def-restricted-routes home-routes
  (GET "/" [] (home)))
