package thebetweenlands.recipes.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

public class PestleAndMortarRecipe {

	private static final List<PestleAndMortarRecipe> recipes = new ArrayList<PestleAndMortarRecipe>();

	/**
	 *
	 * @param output
	 *            what will be produced by the recipe
	 * @param input
	 *            the input item for the recipe
	 */
	public static void addRecipe(ItemStack output, ItemStack input) {
		recipes.add(new PestleAndMortarRecipe(output, input));
	}

	public static ItemStack getOutput(ItemStack input) {
		for (PestleAndMortarRecipe recipe : recipes)
			if (recipe.matches(input))
				return recipe.getOutput();

		return null;
	}

	public static ItemStack getInput(ItemStack output) {
		for (PestleAndMortarRecipe recipe : recipes)
			if (recipe.matchesOutput(output))
				return recipe.getInputs();

		return null;
	}

	public static List<PestleAndMortarRecipe> getRecipeList() {
		return Collections.unmodifiableList(recipes);
	}

	private final ItemStack output;
	private final ItemStack input;

	private PestleAndMortarRecipe(ItemStack output, ItemStack input) {
		this.output = ItemStack.copyItemStack(output);
		this.input = ItemStack.copyItemStack(input);

			if (input instanceof ItemStack)
				input = ItemStack.copyItemStack((ItemStack) input);

			else
				throw new IllegalArgumentException("Input must be an ItemStack");
	}

	public ItemStack getInputs() {
		return ItemStack.copyItemStack(input);
	}

	public ItemStack getOutput() {
		return ItemStack.copyItemStack(output);
	}

	public boolean matches(ItemStack stacks) {
		if (stacks != null)
			if (areStacksTheSame(getInputs(), stacks)) {
				stacks = null;
				return true;
			}
		return false;
	}

	public boolean matchesOutput(ItemStack stacks) {
		if (stacks != null)
			if (areStacksTheSame(getOutput(), stacks)) {
				stacks = null;
				return true;
			}
		return false;
	}

	@SuppressWarnings("unchecked")
	private boolean areStacksTheSame(ItemStack stack, ItemStack target) {
		return areStacksTheSame(stack, target, false);
	}

	public static boolean areStacksTheSame(ItemStack stack1, ItemStack stack2, boolean matchSize) {
		if (stack1 == null || stack2 == null)
			return false;

		if (stack1.getItem() == stack2.getItem())
			if (stack1.getItemDamage() == stack2.getItemDamage())
				if (!matchSize || stack1.stackSize == stack2.stackSize) {
					if (stack1.hasTagCompound() && stack2.hasTagCompound())
						return stack1.getTagCompound().equals(stack2.getTagCompound());
					return stack1.hasTagCompound() == stack2.hasTagCompound();
				}
		return false;
	}
}
