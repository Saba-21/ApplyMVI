package com.app3null.applymvi.CreateFile;

import com.intellij.ide.IdeView;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

import java.util.Objects;

public class CreateFile extends WriteCommandAction.Simple {
    private final Project project;
    private final String className;
    private final String packageName;
    private final PsiDirectory directory;
    private PsiDirectory dirBase, dirApp, dirDomain, dirPresentation, dirBaseActions, dirCustomActions, dirCustomModules;
    private PsiClass basePresenter, baseView, baseActivity, baseFragment, baseAction, navigatorAction, viewStateAction;
    private PsiClass mActivity, mPresenter, mNavigator, mViewState, mView, mFragmentBindingModule, mNavigatorBindingModule, mViewStateAction;


    public CreateFile(AnActionEvent e, String className) {
        super(e.getProject());
        IdeView ideView = e.getRequiredData(LangDataKeys.IDE_VIEW);
        this.project = e.getProject();
        this.className = className.substring(0, 1).toUpperCase() + className.substring(1);
        this.packageName = className.toLowerCase();
        this.directory = ideView.getOrChooseDirectory();
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

        dirApp = getDirectoryByName("app", directory);
        if (dirApp == null && directory.getParentDirectory() != null) {
            dirApp = directory.getParentDirectory().createSubdirectory("app");
        }

        dirDomain = getDirectoryByName("domain", directory);
        if (dirDomain == null && directory.getParentDirectory() != null) {
            dirDomain = directory.getParentDirectory().createSubdirectory("domain");
        }

        dirPresentation = getDirectoryByName(packageName, directory);
        if (dirPresentation == null) {
            dirPresentation = directory.createSubdirectory(packageName);
        }

        dirBaseActions = dirBase.findSubdirectory("baseActions");
        if (dirBaseActions == null) {
            dirBaseActions = dirBase.createSubdirectory("baseActions");
        }

        dirCustomActions = dirPresentation.findSubdirectory("actions");
        if (dirCustomActions == null) {
            dirCustomActions = dirPresentation.createSubdirectory("actions");
        }

        dirCustomModules = dirPresentation.findSubdirectory("modules");
        if (dirCustomModules == null) {
            dirCustomModules = dirPresentation.createSubdirectory("modules");
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
            if (dirPresentation.findFile(className + "Activity.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("Activity.kt"), className + "Activity", null, dirPresentation);
            }
            mActivity = getPsiClassByName(project, className + "Activity", dirPresentation.findFile(className + "Activity.kt"));

            if (dirPresentation.findFile(className + "Presenter.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("Presenter.kt"), className + "Presenter", null, dirPresentation);
            }
            mPresenter = getPsiClassByName(project, className + "Presenter", dirPresentation.findFile(className + "Presenter.kt"));

            if (dirPresentation.findFile(className + "Navigator.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("Navigator.kt"), className + "Navigator", null, dirPresentation);
            }
            mNavigator = getPsiClassByName(project, className + "Navigator", dirPresentation.findFile(className + "Navigator.kt"));

            if (dirPresentation.findFile(className + "ViewState.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("ViewState.kt"), className + "ViewState", null, dirPresentation);
            }
            mViewState = getPsiClassByName(project, className + "ViewState", dirPresentation.findFile(className + "ViewState.kt"));

            if (dirPresentation.findFile(className + "View.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("View.kt"), className + "View", null, dirPresentation);
            }
            mView = getPsiClassByName(project, className + "View", dirPresentation.findFile(className + "View.kt"));

            if (dirCustomModules.findFile(className + "FragmentBindingModule.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("FragmentBindingModule.kt"), className + "FragmentBindingModule", null, dirCustomModules);
            }
            mFragmentBindingModule = getPsiClassByName(project, className + "FragmentBindingModule", dirCustomModules.findFile(className + "FragmentBindingModule.kt"));

            if (dirCustomModules.findFile(className + "NavigatorBindingModule.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("NavigatorBindingModule.kt"), className + "NavigatorBindingModule", null, dirCustomModules);
            }
            mNavigatorBindingModule = getPsiClassByName(project, className + "NavigatorBindingModule", dirCustomModules.findFile(className + "NavigatorBindingModule.kt"));

            if (dirCustomActions.findFile(className + "ViewStateAction.kt") == null) {
                FileTemplateUtil.createFromTemplate(FileTemplateManager.getInstance().getTemplate("ViewStateAction.kt"), className + "ViewStateAction", null, dirCustomActions);
            }
            mViewStateAction = getPsiClassByName(project, className + "ViewStateAction", dirCustomActions.findFile(className + "ViewStateAction.kt"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupDependencies() {

//        ((PsiJavaFile) mPresenter.getContainingFile()).getImportList().add(JavaPsiFacade.getElementFactory(project).createImportStatement(basePresenter));
//
//        if (packageName.equals("aaa")) {
//            Messages.showErrorDialog("true", "Generated");
//            return;
//        }

    }

    @Override
    protected void run() {

        setupDirectories();

        setupBaseClasses();

        setupCustomClasses();

        setupDependencies();

    }
}
