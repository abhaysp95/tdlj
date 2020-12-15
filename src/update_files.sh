#!/usr/bin/env bash

# file: mainwindow.fxml
# source: /opt/SceneBuilder/app
# desitination: src and bin(module)
rsync -av /opt/SceneBuilder/app/mainwindow.fxml /home/raytracer/Downloads/git-materials/my_repos/tdlj/src/com/abhaysp/tdlj
rsync -av /opt/SceneBuilder/app/mainwindow.fxml /home/raytracer/Downloads/git-materials/my_repos/tdlj/bin/com.abhaysp.tdlj/com/abhaysp/tdlj

# file: mainwindow.fxml
# source: src
# desitnation: scenebuilder pos. and bin(module)
rsync -av /home/raytracer/Downloads/git-materials/my_repos/tdlj/src/com/abhaysp/tdlj/mainwindow.fxml /opt/SceneBuilder/app
rsync -av /home/raytracer/Downloads/git-materials/my_repos/tdlj/src/com/abhaysp/tdlj/mainwindow.fxml /home/raytracer/Downloads/git-materials/my_repos/tdlj/bin/com.abhaysp.tdlj/com/abhaysp/tdlj

# provide further sync details here

# file: todoitemdialog.fxml
# source: /opt/SceneBuilder/app
# desitination: src and bin(module)
rsync -av /opt/SceneBuilder/app/todoitemdialog.fxml /home/raytracer/Downloads/git-materials/my_repos/tdlj/src/com/abhaysp/tdlj
rsync -av /opt/SceneBuilder/app/todoitemdialog.fxml /home/raytracer/Downloads/git-materials/my_repos/tdlj/bin/com.abhaysp.tdlj/com/abhaysp/tdlj

# file: todoitemdialog.fxml
# source: src
# desitnation: scenebuilder pos. and bin(module)
rsync -av /home/raytracer/Downloads/git-materials/my_repos/tdlj/src/com/abhaysp/tdlj/todoitemdialog.fxml /opt/SceneBuilder/app
rsync -av /home/raytracer/Downloads/git-materials/my_repos/tdlj/src/com/abhaysp/tdlj/todoitemdialog.fxml /home/raytracer/Downloads/git-materials/my_repos/tdlj/bin/com.abhaysp.tdlj/com/abhaysp/tdlj

# file: TodoListItems.txt
# source: bin
# desitnation: src
#rsync -av "${HOME}/Downloads/git-materials/my_repos/tdlj/bin/TodoListItems.txt" "${HOME}/Downloads/git-materials/my_repos/tdlj/src"

# file: application.css
# source: src
# desitnation: bin(module)
rsync -av /home/raytracer/Downloads/git-materials/my_repos/tdlj/src/com/abhaysp/tdlj/application.css /home/raytracer/Downloads/git-materials/my_repos/tdlj/bin/com.abhaysp.tdlj/com/abhaysp/tdlj