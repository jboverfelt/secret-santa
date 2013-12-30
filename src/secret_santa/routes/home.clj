(ns secret-santa.routes.home
  (:require [compojure.core :refer :all]
            [clostache.parser :as clo]
            [secret-santa.views.layout :as layout]))

(defn home []
  (layout/common (clo/render-resource "templates/hello.mustache" {:person "Justin"})))

(defroutes home-routes
  (GET "/" [] (home)))
