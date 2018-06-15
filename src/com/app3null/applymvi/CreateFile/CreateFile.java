package com.app3null.applymvi.CreateFile;

import com.intellij.ide.IdeView;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

import java.util.Objects;

public class CreateFile extends WriteCommandAction.Simple {
    private final PsiElementFactory factory;
    private final Project project;
    private final String className;
    private final String packageName;
    private final PsiDirectory directory;
    private PsiDirectory dirBase, dirCustom, dirBaseActions, dirCustomActions, dirCustomModules, dirCustomFragment1, dirCustomFragment2, dirCustomFragment1Actions, dirCustomFragment2Actions;
    private PsiClass basePresenter, baseView, baseActivity, baseFragment, baseAction, navigatorAction, viewStateAction;


    public CreateFile(AnActionEvent e, String className) {
        super(e.getProject());
        this.project = e.getProject();
        this.className = className.substring(0, 1).toUpperCase() + className.substring(1);
        this.packageName = className.toLowerCase();
        if (project != null) {
            factory = JavaPsiFacade.getElementFactory(project);
        } else {
            factory = null;
        }
        IdeView ideView = e.getRequiredData(LangDataKeys.IDE_VIEW);
        directory = ideView.getOrChooseDirectory();
    }

    private PsiClass getPsiClassByName(Project project, String name, PsiFile basePresenterPsiFile) {
        PsiShortNamesCache cache = PsiShortNamesCache.getInstance(project);
        GlobalSearchScope search = GlobalSearchScope.fileScope(basePresenterPsiFile);

        PsiClass[] psiClasses = cache.getClassesByName(name, search);
        PsiClass psiClass = null;
        if (psiClasses.length != 0) {
            psiClass = psiClasses[0];
        }
        return psiClass;
    }

    private PsiDirectory getDirectoryByName(String name, PsiDirectory directory) {
        return Objects.requireNonNull(directory.getParentDirectory()).findSubdirectory(name);
    }

    private void setupDirectories() {
        dirBase = getDirectoryByName("base", directory);
        if (dirBase == null && directory.getParentDirectory() != null) {
            dirBase = directory.getParentDirectory().createSubdirectory("base");
        }

        dirCustom = getDirectoryByName(packageName, directory);
        if (dirCustom == null) {
            dirCustom = directory.createSubdirectory(packageName);
        }

        dirBaseActions = dirBase.findSubdirectory("baseActions");
        if (dirBaseActions == null) {
            dirBaseActions = dirBase.createSubdirectory("baseActions");
        }

        dirCustomActions = dirCustom.findSubdirectory("actions");
        if (dirCustomActions == null) {
            dirCustomActions = dirCustom.createSubdirectory("actions");
        }

        dirCustomModules = dirCustom.findSubdirectory("modules");
        if (dirCustomModules == null) {
            dirCustomModules = dirCustom.createSubdirectory("modules");
        }

    }

    private void setupBaseClasses() {
        try {

            if (dirBase.findFile("BasePresenter.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("BasePresenter.kt"), "BasePresenter", null, dirBase);
            }
            basePresenter = getPsiClassByName(project, "BasePresenter", dirBase.findFile("BasePresenter.kt"));

            if (dirBase.findFile("BaseView.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("BaseView.kt"), "BaseView", null, dirBase);
            }
            baseView = getPsiClassByName(project, "BaseView", dirBase.findFile("BaseView.kt"));

            if (dirBase.findFile("BaseActivity.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("BaseActivity.kt"), "BaseActivity", null, dirBase);
            }
            baseActivity = getPsiClassByName(project, "BaseActivity", dirBase.findFile("BaseActivity.kt"));

            if (dirBase.findFile("BaseFragment.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("BaseFragment.kt"), "BaseFragment", null, dirBase);
            }
            baseFragment = getPsiClassByName(project, "BaseFragment", dirBase.findFile("BaseFragment.kt"));

            if (dirBaseActions.findFile("BaseAction.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("BaseAction.kt"), "BaseAction", null, dirBaseActions);
            }
            baseAction = getPsiClassByName(project, "BaseAction", dirBaseActions.findFile("BaseAction.kt"));

            if (dirBaseActions.findFile("BaseNavigatorAction.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("BaseNavigatorAction.kt"), "BaseNavigatorAction", null, dirBaseActions);
            }
            navigatorAction = getPsiClassByName(project, "BaseNavigatorAction", dirBaseActions.findFile("BaseNavigatorAction.kt"));

            if (dirBaseActions.findFile("BaseViewStateAction.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("BaseViewStateAction.kt"), "BaseViewStateAction", null, dirBaseActions);
            }
            viewStateAction = getPsiClassByName(project, "BaseViewStateAction", dirBaseActions.findFile("BaseViewStateAction.kt"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupCustomClasses() {
        try {
            if (dirCustom.findFile(className + "Activity.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("Activity.kt"), className + "Activity", null, dirCustom);
            }
            PsiClass mActivity = getPsiClassByName(project, className + "Activity", dirCustom.findFile(className + "Activity.kt"));

            if (dirCustom.findFile(className + "Presenter.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("Presenter.kt"), className + "Presenter", null, dirCustom);
            }
            PsiClass mPresenter = getPsiClassByName(project, className + "Presenter", dirCustom.findFile(className + "Presenter.kt"));

            if (dirCustom.findFile(className + "Navigator.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("Navigator.kt"), className + "Navigator", null, dirCustom);
            }
            PsiClass mNavigator = getPsiClassByName(project, className + "Navigator", dirCustom.findFile(className + "Navigator.kt"));

            if (dirCustom.findFile(className + "ViewState.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("ViewState.kt"), className + "ViewState", null, dirCustom);
            }
            PsiClass mViewState = getPsiClassByName(project, className + "ViewState", dirCustom.findFile(className + "ViewState.kt"));

            if (dirCustom.findFile(className + "View.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("View.kt"), className + "View", null, dirCustom);
            }
            PsiClass mView = getPsiClassByName(project, className + "View", dirCustom.findFile(className + "View.kt"));

            if (dirCustomModules.findFile(className + "FragmentBindingModule.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("FragmentBindingModule.kt"), className + "FragmentBindingModule", null, dirCustomModules);
            }
            PsiClass mFragmentBindingModule = getPsiClassByName(project, className + "FragmentBindingModule", dirCustomModules.findFile(className + "FragmentBindingModule.kt"));

            if (dirCustomModules.findFile(className + "NavigatorBindingModule.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("NavigatorBindingModule.kt"), className + "NavigatorBindingModule", null, dirCustomModules);
            }
            PsiClass mNavigatorBindingModule = getPsiClassByName(project, className + "NavigatorBindingModule", dirCustomModules.findFile(className + "NavigatorBindingModule.kt"));

            if (dirCustomActions.findFile(className + "ViewStateAction.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("ViewStateAction.kt"), className + "ViewStateAction", null, dirCustomActions);
            }
            PsiClass mViewStateAction = getPsiClassByName(project, className + "ViewStateAction", dirCustomActions.findFile(className + "ViewStateAction.kt"));


        } catch (Exception e) {
            e.printStackTrace();
        }

//        //Create the import required in classes
//        PsiImportStatement importBasePresenter = factory.createImportStatement(basePresenter);
//        PsiImportStatement importBaseView = factory.createImportStatement(baseView);
//        PsiImportStatement importContract = factory.createImportStatement(contract);
//        PsiImportStatement importModel = factory.createImportStatement(model);
//
//        //Add import for classes
//        ((PsiJavaFile) contract.getContainingFile()).getImportList().add(importBasePresenter);
//        ((PsiJavaFile) contract.getContainingFile()).getImportList().add(importBaseView);
//        ((PsiJavaFile) model.getContainingFile()).getImportList().add(importContract);
//        ((PsiJavaFile) presenter.getContainingFile()).getImportList().add(importContract);
//        ((PsiJavaFile) presenter.getContainingFile()).getImportList().add(importModel);
    }

    @Override
    protected void run() {
        if (packageName.equals("aaa")) {
            Messages.showErrorDialog("true", "Generated");
            return;
        }

        setupDirectories();

        setupBaseClasses();

        setupCustomClasses();

    }
}
