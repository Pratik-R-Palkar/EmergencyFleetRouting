"""Root test to verify repository scaffolding and key file presence."""

import os
import unittest


class TestRepositoryScaffold(unittest.TestCase):

    def setUp(self):
        self.root_dir = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

    def test_required_documentation_files_exist(self):
        required_files = [
            "README.md",
            ".gitignore",
            "LICENSE",
            os.path.join("docs", "TEAM_WORKFLOW.md"),
            os.path.join("docs", "API_CONTRACT.md"),
            os.path.join("docs", "MODULE_OWNERSHIP.md"),
        ]
        for rel_path in required_files:
            full_path = os.path.join(self.root_dir, rel_path)
            self.assertTrue(os.path.isfile(full_path), f"Missing required file: {rel_path}")

    def test_required_backend_modules_exist(self):
        required_dirs = [
            os.path.join("backend", "api"),
            os.path.join("backend", "database"),
            os.path.join("backend", "models"),
            os.path.join("backend", "routing"),
            os.path.join("backend", "optimization"),
            os.path.join("backend", "simulation"),
            os.path.join("backend", "tests"),
        ]
        for rel_path in required_dirs:
            full_path = os.path.join(self.root_dir, rel_path)
            self.assertTrue(os.path.isdir(full_path), f"Missing backend dir: {rel_path}")

    def test_required_android_skeleton_exists(self):
        android_dir = os.path.join(self.root_dir, "android", "EmergencyFleetRouting")
        self.assertTrue(os.path.isdir(android_dir), f"Missing android dir: {android_dir}")


if __name__ == "__main__":
    unittest.main()
