package Utils.OtherTool;

import Base.GameFrame;
import Utils.GUI.FlexGrid;

import java.util.Arrays;

public class CraftIndex {
    // 更新合成配方
    public static void updateCrafting(GameFrame game) {
        updateRecipe(game);
        // 判断是否合成
        if (game.isCrafted()) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() >= 41 && flexGrid.getSort() <= 49 && flexGrid.getId() != -1)
                    flexGrid.setAmount(flexGrid.getAmount() - 1);
            }
            game.setCrafted(false);
        }
        // 判断是否全部合成
        if (game.isAllCrafted()) {
            while (updateRecipe(game)) {
                int idRecipe = -1;
                int amountRecipe = 0;
                for (FlexGrid flexGrid : game.getFlexGridList()) {
                    if (flexGrid.getSort() == 50) {
                        idRecipe = flexGrid.getId();
                        amountRecipe = flexGrid.getAmount();
                    }
                }
                for (FlexGrid flexGrid : game.getFlexGridList()) {
                    if (flexGrid.getSort() >= 41 && flexGrid.getSort() <= 49 && flexGrid.getId() != -1) {
                        flexGrid.setAmount(flexGrid.getAmount() - 1);
                        if (flexGrid.getAmount() == 0) flexGrid.setId(-1);
                    }
                }
                game.getPlayer().getItem(idRecipe, amountRecipe, 36, false);
            }
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                flexGrid.updateItemGrid();
            }
            game.setAllCrafted(false);
        }
    }

    public static boolean updateRecipe(GameFrame game) {
        int[] craftIdArray = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
        // 刷新配方Id序列
        for (FlexGrid flexGrid : game.getFlexGridList()) {
            for (int i = 1; i <= 9; i++) {
                if (flexGrid.getSort() == 40 + i) {
                    craftIdArray[i - 1] = flexGrid.getId();
                    break;
                }
            }
        }
        // 判断是否有生成物
        boolean recipeExisted = false;
        // 分解原木
        int amountOak = 0;
        int amountOther = 0;
        for (int i = 0; i < 9; i++) {
            if (craftIdArray[i] == 5) amountOak++;
            else if (craftIdArray[i] != -1) amountOther++;
        }
        if (amountOak == 1 && amountOther == 0) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(4);
                    flexGrid.setId(6);
                }
            }
            recipeExisted = true;
        }
        // 分解铁块
        int amountIronBlock = 0;
        amountOther = 0;
        for (int i = 0; i < 9; i++) {
            if (craftIdArray[i] == 27) amountIronBlock++;
            else if (craftIdArray[i] != -1) amountOther++;
        }
        if (amountIronBlock == 1 && amountOther == 0) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(9);
                    flexGrid.setId(24);
                }
            }
            recipeExisted = true;
        }
        // 分解金块
        int amountGoldBlock = 0;
        amountOther = 0;
        for (int i = 0; i < 9; i++) {
            if (craftIdArray[i] == 28) amountGoldBlock++;
            else if (craftIdArray[i] != -1) amountOther++;
        }
        if (amountGoldBlock == 1 && amountOther == 0) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(9);
                    flexGrid.setId(25);
                }
            }
            recipeExisted = true;
        }
        // 分解钻石块
        int amountDiamondBlock = 0;
        amountOther = 0;
        for (int i = 0; i < 9; i++) {
            if (craftIdArray[i] == 29) amountDiamondBlock++;
            else if (craftIdArray[i] != -1) amountOther++;
        }
        if (amountDiamondBlock == 1 && amountOther == 0) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(9);
                    flexGrid.setId(26);
                }
            }
            recipeExisted = true;
        }
        // 合成木棍
        int amountStick = 0;
        amountOther = 0;
        for (int i = 0; i < 9; i++) {
            if (craftIdArray[i] != -1 && craftIdArray[i] != 6) amountOther++;
            else if (craftIdArray[i] == 6) {
                if ((i < 6 && craftIdArray[i + 3] == 6)) amountStick++;
                else if (i < 3 && craftIdArray[i + 3] != 6) amountOther++;
                else if (i >= 3 && craftIdArray[i - 3] != 6) amountOther++;
            }
        }
        if (amountStick == 1 && amountOther == 0) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(4);
                    flexGrid.setId(18);
                }
            }
            recipeExisted = true;
        }
        // 合成火把
        int amountTorch = 0;
        amountOther = 0;
        for (int i = 0; i < 9; i++) {
            if (craftIdArray[i] != -1 && !(craftIdArray[i] == 18 || craftIdArray[i] == 23)) amountOther++;
            else if (craftIdArray[i] == 18) {
                if ((i < 6 && craftIdArray[i + 3] == 23)) amountTorch++;
                else if (i < 6 && craftIdArray[i + 3] != 23) amountOther++;
                else if (i >= 6) amountOther++;
            } else if (craftIdArray[i] == 23) {
                if ((i > 2 && craftIdArray[i - 3] == 18)) amountTorch++;
                else if (i > 2 && craftIdArray[i - 3] != 18) amountOther++;
                else if (i <= 2) amountOther++;
            }
        }
        if (amountTorch == 2 && amountOther == 0) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(4);
                    flexGrid.setId(17);
                }
            }
            recipeExisted = true;
        }
        // 合成工作台
        int[] planks, others;
        boolean canCraft = false;
        planks = new int[]{0, 1, 3, 4};
        others = new int[]{2, 5, 6, 7, 8};
        if (Arrays.stream(planks).allMatch(integer -> craftIdArray[integer] == 6)) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        planks = new int[]{1, 2, 4, 5};
        others = new int[]{0, 3, 6, 7, 8};
        if (Arrays.stream(planks).allMatch(integer -> craftIdArray[integer] == 6)) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        planks = new int[]{3, 4, 6, 7};
        others = new int[]{0, 1, 2, 5, 8};
        if (Arrays.stream(planks).allMatch(integer -> craftIdArray[integer] == 6)) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        planks = new int[]{4, 5, 7, 8};
        others = new int[]{0, 1, 2, 3, 6};
        if (Arrays.stream(planks).allMatch(integer -> craftIdArray[integer] == 6)) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(1);
                    flexGrid.setId(8);
                }
            }
            recipeExisted = true;
        }
        // 合成木剑
        canCraft = false;
        others = new int[]{1, 2, 4, 5, 7, 8};
        if (craftIdArray[0] == 18 && craftIdArray[3] == 6 && craftIdArray[6] == 6) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 2, 3, 5, 6, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 6 && craftIdArray[7] == 6) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 4, 6, 7};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 6 && craftIdArray[8] == 6) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(9));
                    flexGrid.setId(9);
                }
            }
            recipeExisted = true;
        }
        // 合成木锹
        canCraft = false;
        others = new int[]{1, 2, 4, 5, 7, 8};
        if (craftIdArray[0] == 18 && craftIdArray[3] == 18 && craftIdArray[6] == 6) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 2, 3, 5, 6, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 6) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 4, 6, 7};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 18 && craftIdArray[8] == 6) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(12));
                    flexGrid.setId(12);
                }
            }
            recipeExisted = true;
        }
        // 合成木斧
        canCraft = false;
        others = new int[]{0, 2, 5, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 6 && craftIdArray[3] == 6 && craftIdArray[6] == 6) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 6};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 18 && craftIdArray[8] == 6 && craftIdArray[4] == 6 && craftIdArray[7] == 6) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(11));
                    flexGrid.setId(11);
                }
            }
            recipeExisted = true;
        }
        // 合成木稿
        canCraft = false;
        others = new int[]{0, 2, 3, 5};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 6 && craftIdArray[6] == 6 && craftIdArray[8] == 6) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(10));
                    flexGrid.setId(10);
                }
            }
            recipeExisted = true;
        }
        // 合成石剑
        canCraft = false;
        others = new int[]{1, 2, 4, 5, 7, 8};
        if (craftIdArray[0] == 18 && craftIdArray[3] == 4 && craftIdArray[6] == 4) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 2, 3, 5, 6, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 4 && craftIdArray[7] == 4) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 4, 6, 7};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 4 && craftIdArray[8] == 4) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(13));
                    flexGrid.setId(13);
                }
            }
            recipeExisted = true;
        }
        // 合成石锹
        canCraft = false;
        others = new int[]{1, 2, 4, 5, 7, 8};
        if (craftIdArray[0] == 18 && craftIdArray[3] == 18 && craftIdArray[6] == 4) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 2, 3, 5, 6, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 4) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 4, 6, 7};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 18 && craftIdArray[8] == 4) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(16));
                    flexGrid.setId(16);
                }
            }
            recipeExisted = true;
        }
        // 合成石斧
        canCraft = false;
        others = new int[]{0, 2, 5, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 4 && craftIdArray[3] == 4 && craftIdArray[6] == 4) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 6};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 18 && craftIdArray[8] == 4 && craftIdArray[4] == 4 && craftIdArray[7] == 4) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(15));
                    flexGrid.setId(15);
                }
            }
            recipeExisted = true;
        }
        // 合成石稿
        canCraft = false;
        others = new int[]{0, 2, 3, 5};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 4 && craftIdArray[6] == 4 && craftIdArray[8] == 4) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(14));
                    flexGrid.setId(14);
                }
            }
            recipeExisted = true;
        }
        // 合成铁剑
        canCraft = false;
        others = new int[]{1, 2, 4, 5, 7, 8};
        if (craftIdArray[0] == 18 && craftIdArray[3] == 24 && craftIdArray[6] == 24) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 2, 3, 5, 6, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 24 && craftIdArray[7] == 24) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 4, 6, 7};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 24 && craftIdArray[8] == 24) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(30));
                    flexGrid.setId(30);
                }
            }
            recipeExisted = true;
        }
        // 合成铁锹
        canCraft = false;
        others = new int[]{1, 2, 4, 5, 7, 8};
        if (craftIdArray[0] == 18 && craftIdArray[3] == 18 && craftIdArray[6] == 24) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 2, 3, 5, 6, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 24) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 4, 6, 7};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 18 && craftIdArray[8] == 24) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(33));
                    flexGrid.setId(33);
                }
            }
            recipeExisted = true;
        }
        // 合成铁斧
        canCraft = false;
        others = new int[]{0, 2, 5, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 24 && craftIdArray[3] == 24 && craftIdArray[6] == 24) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 6};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 18 && craftIdArray[8] == 24 && craftIdArray[4] == 24 && craftIdArray[7] == 24) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(32));
                    flexGrid.setId(32);
                }
            }
            recipeExisted = true;
        }
        // 合成铁稿
        canCraft = false;
        others = new int[]{0, 2, 3, 5};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 24 && craftIdArray[6] == 24 && craftIdArray[8] == 24) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(31));
                    flexGrid.setId(31);
                }
            }
            recipeExisted = true;
        }
        // 合成金剑
        canCraft = false;
        others = new int[]{1, 2, 4, 5, 7, 8};
        if (craftIdArray[0] == 18 && craftIdArray[3] == 25 && craftIdArray[6] == 25) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 2, 3, 5, 6, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 25 && craftIdArray[7] == 25) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 4, 6, 7};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 25 && craftIdArray[8] == 25) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(34));
                    flexGrid.setId(34);
                }
            }
            recipeExisted = true;
        }
        // 合成金锹
        canCraft = false;
        others = new int[]{1, 2, 4, 5, 7, 8};
        if (craftIdArray[0] == 18 && craftIdArray[3] == 18 && craftIdArray[6] == 25) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 2, 3, 5, 6, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 25) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 4, 6, 7};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 18 && craftIdArray[8] == 25) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(37));
                    flexGrid.setId(37);
                }
            }
            recipeExisted = true;
        }
        // 合成金斧
        canCraft = false;
        others = new int[]{0, 2, 5, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 25 && craftIdArray[3] == 25 && craftIdArray[6] == 25) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 6};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 18 && craftIdArray[8] == 25 && craftIdArray[4] == 25 && craftIdArray[7] == 25) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(36));
                    flexGrid.setId(36);
                }
            }
            recipeExisted = true;
        }
        // 合成金稿
        canCraft = false;
        others = new int[]{0, 2, 3, 5};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 25 && craftIdArray[6] == 25 && craftIdArray[8] == 25) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(35));
                    flexGrid.setId(35);
                }
            }
            recipeExisted = true;
        }
        // 合成钻石剑
        canCraft = false;
        others = new int[]{1, 2, 4, 5, 7, 8};
        if (craftIdArray[0] == 18 && craftIdArray[3] == 26 && craftIdArray[6] == 26) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 2, 3, 5, 6, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 26 && craftIdArray[7] == 26) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 4, 6, 7};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 26 && craftIdArray[8] == 26) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(38));
                    flexGrid.setId(38);
                }
            }
            recipeExisted = true;
        }
        // 合成钻石锹
        canCraft = false;
        others = new int[]{1, 2, 4, 5, 7, 8};
        if (craftIdArray[0] == 18 && craftIdArray[3] == 18 && craftIdArray[6] == 26) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 2, 3, 5, 6, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 26) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 4, 6, 7};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 18 && craftIdArray[8] == 26) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(41));
                    flexGrid.setId(41);
                }
            }
            recipeExisted = true;
        }
        // 合成钻石斧
        canCraft = false;
        others = new int[]{0, 2, 5, 8};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 26 && craftIdArray[3] == 26 && craftIdArray[6] == 26) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        others = new int[]{0, 1, 3, 6};
        if (craftIdArray[2] == 18 && craftIdArray[5] == 18 && craftIdArray[8] == 26 && craftIdArray[4] == 26 && craftIdArray[7] == 26) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(40));
                    flexGrid.setId(40);
                }
            }
            recipeExisted = true;
        }
        // 合成钻石稿
        canCraft = false;
        others = new int[]{0, 2, 3, 5};
        if (craftIdArray[1] == 18 && craftIdArray[4] == 18 && craftIdArray[7] == 26 && craftIdArray[6] == 26 && craftIdArray[8] == 26) {
            if (Arrays.stream(others).allMatch(integer -> craftIdArray[integer] == -1))
                canCraft = true;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(IDIndex.blockIdToMaxAmount(39));
                    flexGrid.setId(39);
                }
            }
            recipeExisted = true;
        }
        // 合成铁块
        canCraft = true;
        for (int i = 0; i < 9; i++) {
            if (craftIdArray[i] != 24) canCraft = false;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(1);
                    flexGrid.setId(27);
                }
            }
            recipeExisted = true;
        }
        // 合成金块
        canCraft = true;
        for (int i = 0; i < 9; i++) {
            if (craftIdArray[i] != 25) canCraft = false;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(1);
                    flexGrid.setId(28);
                }
            }
            recipeExisted = true;
        }
        // 合成钻石块
        canCraft = true;
        for (int i = 0; i < 9; i++) {
            if (craftIdArray[i] != 26) canCraft = false;
        }
        if (canCraft) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(1);
                    flexGrid.setId(29);
                }
            }
            recipeExisted = true;
        }
        // 判断是否有生成物
        if (!recipeExisted) {
            for (FlexGrid flexGrid : game.getFlexGridList()) {
                if (flexGrid.getSort() == 50) {
                    flexGrid.setAmount(0);
                    flexGrid.setId(-1);
                }
            }
        }
        return recipeExisted;
    }
}
