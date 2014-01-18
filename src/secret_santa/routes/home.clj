(ns secret-santa.routes.home
  (:require [compojure.core :refer :all]
            [clostache.parser :as clo]
            [noir.util.route :refer [def-restricted-routes]]
            [secret-santa.views.layout :as layout]))

(defn home []
  (layout/common (clo/render-resource "templates/hello.mustache" {:person "Justin"})))

(def-restricted-routes home-routes
  (GET "/" [] (home)))
