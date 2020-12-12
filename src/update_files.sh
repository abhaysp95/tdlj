#!/usr/bin/env bash

# file: mainwindow.fxml
# source: /opt/SceneBuilder/app
# desitination: src and bin(module)
rsync -av /opt/SceneBuilder/app/mainwindow.fxml /home/raytracer/Downloads/git-materials/my_repos/tdlj/src/com/abhaysp/tdlj
rsync -av /opt/SceneBuilder/app/mainwindow.fxml /home/raytracer/Downloads/git-materials/my_repos/tdlj/bin/com.abhaysp.tdlj/com/abhaysp/tdlj

# file: mainwindow.fxml
# source: src
# desitnation: scenebuilder pos. and bin(module)
rsync -a /home/raytracer/Downloads/git-materials/my_repos/tdlj/src/com/abhaysp/tdlj/mainwindow.fxml /opt/SceneBuilder/app
rsync -a /home/raytracer/Downloads/git-materials/my_repos/tdlj/src/com/abhaysp/tdlj/mainwindow.fxml /home/raytracer/Downloads/git-materials/my_repos/tdlj/bin/com.abhaysp.tdlj/com/abhaysp/tdlj

# provide further sync details here