(ns secret-santa.routes.wishlist
  (:require [compojure.core :refer :all]
            [clostache.parser :as clo]
            [secret-santa.views.layout :as layout]
            [secret-santa.models.wishlist :as wishlist]))

(defn wishlist []
  (layout/common (clo/render-resource "templates/wishlist.mustache" {})))

(defroutes wishlist-routes
  (GET "/wishlist" [] (wishlist)))
